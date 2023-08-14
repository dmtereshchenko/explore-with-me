package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Constant;
import ru.practicum.main.category.storage.CategoryRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.event.storage.LocationRepository;
import ru.practicum.main.exception.*;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResponse;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.storage.RequestRepository;
import ru.practicum.main.user.storage.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateEventServiceImpl implements PrivateEventService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public EventFullDto create(Long userId, NewEventDto newEventDto) {
        return EventMapper.toFullDto(eventRepository.save(EventMapper.toEvent(
                newEventDto,
                categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() -> new CategoryNotFoundException(newEventDto.getCategory())),
                locationRepository.save(newEventDto.getLocation()),
                userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)))));
    }

    public EventFullDto update(Long userId, Long eventId, UpdateEventRequest request) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (!event.getInitiator().getId().equals(userId))
            throw new ActionNotAvailableException("Невозможно редактировать чужое событие.");
        if (event.getState().equals(Constant.State.PUBLISHED))
            throw new SomethingWentWrongException("Невозможно редактировать опубликованное событие.");
        return EventMapper.toFullDto(eventRepository.save(EventMapper.toEventUpdated(event,
                request,
                request.getCategory() == null ? event.getCategory() : categoryRepository.findById(request.getCategory()).get(),
                request.getLocation() == null ? event.getLocation() : locationRepository.save(request.getLocation()))
        ));
    }

    public EventRequestStatusUpdateResponse updateRequestsStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest requestsForUpdate) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (!event.getInitiator().getId().equals(userId))
            throw new ActionNotAvailableException("Невозможно редактировать список участников чужого события.");
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0)
            throw new ActionNotAvailableException("Запись на это событие невозмонжа");
        if (requestsForUpdate.getStatus().equals(Constant.StateParticipation.CONFIRMED) &&
                requestRepository.findRequestsByEventIds(List.of(eventId)).size() + requestsForUpdate.getRequestIds().size() > event.getParticipantLimit()) {
            throw new SomethingWentWrongException("Невозможно превысить лимит участников события.");
        }
        List<Request> requests = requestRepository.findAllById(requestsForUpdate.getRequestIds());
        if (requestsForUpdate.getStatus().equals(Constant.StateParticipation.CONFIRMED)) {
            requests.forEach(request -> request.setStatus(Constant.StateParticipation.CONFIRMED));
            requestRepository.saveAll(requests);
            return new EventRequestStatusUpdateResponse(requests.stream()
                    .map(RequestMapper::toDto)
                    .collect(Collectors.toList()), new ArrayList<>());
        } else {
            requests.forEach(request -> {
                if (request.getStatus() == Constant.StateParticipation.CONFIRMED) {
                    throw new SomethingWentWrongException("Нельзя отклонить уже подтвержденный запрос.");
                }
                request.setStatus(Constant.StateParticipation.REJECTED);
            });
            requestRepository.saveAll(requests);
            return new EventRequestStatusUpdateResponse(new ArrayList<>(),
                    requests.stream()
                            .map(RequestMapper::toDto)
                            .collect(Collectors.toList()));
        }
    }

    @Transactional(readOnly = true)
    public EventFullDto get(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (!event.getInitiator().getId().equals(userId))
            throw new ActionNotAvailableException("Невозможно редактировать чужое событие.");
        return EventMapper.toFullDto(event);
    }

    @Transactional(readOnly = true)
    public List<EventShortDto> getAll(Long userId, int from, int size) {
        return eventRepository.findEventsByInitiator(
                        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)),
                        PageRequest.of(from / size, size))
                .stream().map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getAllParticipationsByEvent(Long userId, Long eventId) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (!event.getInitiator().getId().equals(userId))
            throw new ActionNotAvailableException("Невозможно просматривать участников чужого события.");
        return requestRepository.findAllEventRequestsByEvent(event).stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }
}
