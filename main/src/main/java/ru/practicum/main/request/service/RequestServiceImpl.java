package ru.practicum.main.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Constant;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.exception.EventNotFoundException;
import ru.practicum.main.exception.RequestNotFoundException;
import ru.practicum.main.exception.SomethingWentWrongException;
import ru.practicum.main.exception.UserNotFoundException;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.storage.RequestRepository;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.storage.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public ParticipationRequestDto create(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (event.getInitiator().getId().equals(userId))
            throw new SomethingWentWrongException("Нельзя отправить запрос на свое событие.");
        if (!event.getState().equals(Constant.State.PUBLISHED)) {
            throw new SomethingWentWrongException("Нельзя отправить запрос на участие в неподтвержденном событии.");
        }
        if (requestRepository.findByRequesterIdAndEventId(userId, eventId) != null) {
            throw new SomethingWentWrongException("Нельзя отправить запрос повторно.");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() ==
                requestRepository.findAllEventRequestsByEventAndStatus(event, Constant.StateParticipation.CONFIRMED).size()) {
            throw new SomethingWentWrongException("Нет доступных мест для участия в этом событии");
        }
        return RequestMapper.toDto(requestRepository.save(new Request(
                null,
                event,
                user,
                !event.isRequestModeration() || event.getParticipantLimit() == 0 ? Constant.StateParticipation.CONFIRMED : Constant.StateParticipation.PENDING,
                LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER)
        )));
    }

    public ParticipationRequestDto update(Long userId, Long requestId) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new RequestNotFoundException(requestId));
        if (!request.getRequester().getId().equals(userId)) throw new RequestNotFoundException(requestId);
        request.setStatus(Constant.StateParticipation.CANCELED);
        return RequestMapper.toDto(requestRepository.save(request));
    }

    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getAll(Long userId) {
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);
        return requestRepository.findRequestsByRequesterId(userId).stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }
}
