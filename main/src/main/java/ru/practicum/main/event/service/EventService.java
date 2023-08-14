package ru.practicum.main.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndPointHit;
import ru.practicum.dto.ViewStat;
import ru.practicum.main.comment.dto.CommentShortDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.request.model.ConfirmedRequests;
import ru.practicum.main.request.storage.RequestRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final RequestRepository requestRepository;
    private final CommentRepository commentRepository;
    private final StatsClient stats;

    protected Map<Long, Integer> getConfirmedRequests(List<Event> events) {
        return requestRepository.findRequestsByEventIds(events.stream().map(Event::getId)
                        .collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(k -> k.getEventId(), ConfirmedRequests::getCount));
    }

    protected Map<Long, Long> getStats(List<Event> events) {
        return stats.get(events.stream()
                        .map(event -> "/events/" + event.getId())
                        .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(k -> Long.valueOf(k.getUri().replace("/events/", "")),
                        ViewStat::getHits));
    }

    protected Map<Long, List<CommentShortDto>> getComments(List<Event> events) {
        List<Comment> comments = commentRepository.findAllByEventIdIn(events.stream().map(event -> event.getId()).collect(Collectors.toList()));
        if (comments.size() == 0) return new HashMap<>();
        Map<Long, List<CommentShortDto>> commentsMap = new HashMap<>();
        for (Comment comment : comments) {
            if (commentsMap.get(comment.getEvent().getId()) == null) {
                commentsMap.put(comment.getEvent().getId(), List.of(CommentMapper.toShortDto(comment)));
            } else {
                commentsMap.get(comment.getEvent().getId()).add(CommentMapper.toShortDto(comment));
            }
        }
        return commentsMap;
    }

    protected void sendViews(HttpServletRequest request) {
        stats.create(new EndPointHit(
                null,
                "ewm-main-service",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now()
        ));
    }
}
