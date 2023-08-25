package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventRequestByParams;
import ru.practicum.main.event.dto.UpdateEventRequest;
import ru.practicum.main.event.service.AdminEventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class AdminEventController {

    private final AdminEventService service;

    @PatchMapping("/{eventId}")
    EventFullDto update(@PathVariable Long eventId,
                        @Valid @RequestBody UpdateEventRequest request) {
        log.info("Получен запрос PATCH /admin/events/{}/", eventId);
        return service.update(eventId, request);
    }

    @GetMapping
    List<EventFullDto> getAll(@Valid EventRequestByParams request) {
        log.info("Получен запрос GET /admin/events/");
        return service.getAll(request);
    }
}
