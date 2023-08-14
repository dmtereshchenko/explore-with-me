package ru.practicum.main.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Constant;
import ru.practicum.client.StatsClient;
import ru.practicum.main.comment.dto.CommentShortDto;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventRequestByParams;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.storage.EventRepository;
import ru.practicum.main.exception.EventNotFoundException;
import ru.practicum.main.exception.InvalidDataException;
import ru.practicum.main.request.storage.RequestRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PublicEventServiceImpl extends EventService implements PublicEventService {

    private final EventRepository eventRepository;

    @Autowired
    public PublicEventServiceImpl(EventRepository eventRepository, RequestRepository requestRepository,
                                  CommentRepository commentRepository, StatsClient stats) {
        super(requestRepository, commentRepository, stats);
        this.eventRepository = eventRepository;
    }

    @Override
    public EventFullDto get(Long eventId, HttpServletRequest servletRequest) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        if (event.getState() != Constant.State.PUBLISHED) throw new EventNotFoundException(eventId);
        sendViews(servletRequest);
        Map<Long, Integer> confirmedRequests = getConfirmedRequests(List.of(event));
        Map<Long, Long> views = getStats(List.of(event));
        Map<Long, List<CommentShortDto>> comments = getComments(List.of(event));
        return EventMapper.toFullDto(event,
                views.get(eventId) != null ? views.get(event.getId()) : 0,
                confirmedRequests.get(eventId) != null ? confirmedRequests.get(event.getId()) : 0,
                comments.size() != 0 ? comments.get(eventId) : new ArrayList<>());
    }

    @Override
    public List<EventFullDto> getAll(EventRequestByParams request, HttpServletRequest servletRequest) {
        if (request.getRangeStart() != null && request.getRangeEnd() != null && request.getRangeEnd().isBefore(request.getRangeStart())) {
            throw new InvalidDataException("Время окончания не может быть раньше времени старта.");
        }
        sendViews(servletRequest);
        List<Event> events = eventRepository.findAllByRequest(request);
        if (events.size() == 0) return new ArrayList<>();
        Map<Long, Integer> confirmedRequests = getConfirmedRequests(events);
        Map<Long, Long> views = getStats(events);
        Map<Long, List<CommentShortDto>> comments = getComments(events);
        return events.stream().map(event -> EventMapper.toFullDto(
                        event,
                        views.get(event.getId()) != null ? views.get(event.getId()) : 0,
                        confirmedRequests.get(event.getId()) != null ? confirmedRequests.get(event.getId()) : 0,
                        comments.get(event.getId()) != null ? comments.get(event.getId()) : new ArrayList<>()
                ))
                .sorted(request.getSort() != null && request.getSort().equals(Constant.UserRequestSort.VIEWS) ?
                        Comparator.comparingLong(EventFullDto::getViews) : EventFullDto::compareTo)
                .collect(Collectors.toList());
    }
}
