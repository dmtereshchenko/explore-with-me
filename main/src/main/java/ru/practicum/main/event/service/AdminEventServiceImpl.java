package ru.practicum.main.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Constant;
import ru.practicum.client.StatsClient;
import ru.practicum.main.category.storage.CategoryRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventRequestByParams;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.event.storage.LocationRepository;
import ru.practicum.main.exception.EventNotFoundException;
import ru.practicum.main.exception.InvalidDataException;
import ru.practicum.main.exception.SomethingWentWrongException;
import ru.practicum.main.request.storage.RequestRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdminEventServiceImpl extends EventService implements AdminEventService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public AdminEventServiceImpl(RequestRepository requestRepository, CategoryRepository categoryRepository, EventRepository eventRepository, LocationRepository locationRepository, StatsClient stats) {
        super(requestRepository, stats);
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }


    public EventFullDto update(Long eventId, UpdateEventRequest request) {
        if (request.getEventDate() != null && request.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new InvalidDataException("Время начала события не может быть ранее, чем через час");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (!event.getState().equals(Constant.State.PENDING)) {
            throw new SomethingWentWrongException("Невозможно изменить отклоненное или опубликованное событие.");
        }
        event = EventMapper.toEventUpdated(
                event,
                request,
                request.getCategory() == null ? event.getCategory() : categoryRepository.findById(request.getCategory()).get(),
                request.getLocation() == null ? event.getLocation() : locationRepository.save(request.getLocation())
        );
        if (event.getState().equals(Constant.State.PUBLISHED)) {
            event.setPublishedOn(LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER));
        }
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    @Transactional(readOnly = true)
    public List<EventFullDto> getAll(EventRequestByParams request) {
        List<Event> events = eventRepository.findAllByRequest(request);
        if (events.size() == 0) return new ArrayList<>();
        Map<Long, Integer> confirmedRequests = getConfirmedRequests(events);
        Map<Long, Long> views = getStats(events);
        return events.stream().map(event -> EventMapper.toFullDto(
                        event,
                        views.get(event.getId()) != null ? views.get(event.getId()) : 0,
                        confirmedRequests.get(event.getId()) != null ? confirmedRequests.get(event.getId()) : 0
                ))
                .collect(Collectors.toList());
    }
}
