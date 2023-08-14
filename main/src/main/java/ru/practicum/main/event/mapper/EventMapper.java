package ru.practicum.main.event.mapper;

import ru.practicum.Constant;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.comment.dto.CommentShortDto;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.Location;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class EventMapper {

    public static Event toEvent(NewEventDto newEventDto, Category category, Location location, User initiator) {
        return new Event(
                null,
                category,
                location,
                initiator,
                newEventDto.getParticipantLimit(),
                newEventDto.isPaid(),
                newEventDto.isRequestModeration(),
                newEventDto.getAnnotation(),
                newEventDto.getDescription(),
                Constant.State.PENDING,
                newEventDto.getTitle(),
                LocalDateTime.parse(LocalDateTime.now().format(Constant.FORMATTER), Constant.FORMATTER),
                newEventDto.getEventDate(),
                null
        );
    }

    public static EventFullDto toFullDto(Event event) {
        return new EventFullDto(
                event.getId(),
                CategoryMapper.toDto(event.getCategory()),
                event.getLocation(),
                UserMapper.toShortDto(event.getInitiator()),
                null,
                0,
                0,
                event.getParticipantLimit(),
                event.isPaid(),
                event.isRequestModeration(),
                event.getAnnotation(),
                event.getDescription(),
                event.getState(),
                event.getTitle(),
                event.getCreatedOn(),
                event.getEventDate(),
                event.getPublishedOn()
        );
    }

    public static EventFullDto toFullDto(Event event, long views, long confirmedRequests, List<CommentShortDto> comments) {
        return new EventFullDto(
                event.getId(),
                CategoryMapper.toDto(event.getCategory()),
                event.getLocation(),
                UserMapper.toShortDto(event.getInitiator()),
                comments,
                confirmedRequests,
                views,
                event.getParticipantLimit(),
                event.isPaid(),
                event.isRequestModeration(),
                event.getAnnotation(),
                event.getDescription(),
                event.getState(),
                event.getTitle(),
                event.getCreatedOn(),
                event.getEventDate(),
                event.getPublishedOn()
        );
    }

    public static EventShortDto toShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                CategoryMapper.toDto(event.getCategory()),
                UserMapper.toShortDto(event.getInitiator()),
                event.isPaid(),
                event.getAnnotation(),
                event.getTitle(),
                event.getEventDate()
        );
    }

    public static Event toEventUpdated(Event event, UpdateEventRequest request, Category category, Location location) {
        return new Event(
                event.getId(),
                category,
                location,
                event.getInitiator(),
                Objects.requireNonNullElse(request.getParticipantLimit(), event.getParticipantLimit()),
                Objects.requireNonNullElse(request.getPaid(), event.isPaid()),
                Objects.requireNonNullElse(request.getRequestModeration(), event.isRequestModeration()),
                Objects.requireNonNullElse(request.getAnnotation(), event.getAnnotation()),
                Objects.requireNonNullElse(request.getDescription(), event.getDescription()),
                request.getStateAction() == null ? event.getState() : StateMapper.toState(request.getStateAction()),
                Objects.requireNonNullElse(request.getTitle(), event.getTitle()),
                event.getCreatedOn(),
                Objects.requireNonNullElse(request.getEventDate(), event.getEventDate()),
                event.getPublishedOn()
        );
    }
}
