package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.service.PrivateEventService;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResponse;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventController {

    private final PrivateEventService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto create(@PathVariable Long userId,
                        @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Получен запрос POST /users/{}/events/", userId);
        return service.create(userId, newEventDto);
    }

    @PatchMapping("/{eventId}")
    EventFullDto update(@PathVariable Long userId,
                        @PathVariable Long eventId,
                        @Valid @RequestBody UpdateEventRequest request) {
        log.info("Получен запрос PATCH /users/{}/events/{}/", userId, eventId);
        return service.update(userId, eventId, request);
    }

    @PatchMapping("/{eventId}/requests")
    EventRequestStatusUpdateResponse updateRequestsStatus(@PathVariable Long userId,
                                                          @PathVariable Long eventId,
                                                          @RequestBody EventRequestStatusUpdateRequest request) {
        log.info("Получен запрос PATCH /users/{}/events/{}/requests/", userId, eventId);
        return service.updateRequestsStatus(userId, eventId, request);
    }

    @GetMapping("/{eventId}")
    EventFullDto get(@PathVariable Long userId,
                     @PathVariable Long eventId) {
        log.info("Получен запрос GET /users/{}/events/{}/", userId, eventId);
        return service.get(userId, eventId);
    }

    @GetMapping
    List<EventShortDto> getAll(@PathVariable Long userId,
                               @RequestParam(defaultValue = "0") int from,
                               @RequestParam(defaultValue = "10") int size) {
        log.info("Получен запрос GET /users/{}/events/", userId);
        return service.getAll(userId, from, size);
    }

    @GetMapping("/{eventId}/requests")
    List<ParticipationRequestDto> getAllParticipationsByEvent(@PathVariable Long userId,
                                                              @PathVariable Long eventId) {
        log.info("Получен запрос GET /users/{}/events/{}/requests/", userId, eventId);
        return service.getAllParticipationsByEvent(userId, eventId);
    }
}
