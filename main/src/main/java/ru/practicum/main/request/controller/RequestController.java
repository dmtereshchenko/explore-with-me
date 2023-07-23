package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.service.RequestService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class RequestController {

    private final RequestService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto create(@PathVariable Long userId,
                                   @RequestParam Long eventId) {
        log.info("Получен запрос POST /users/{}/request/ для события {}", userId, eventId);
        return service.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    ParticipationRequestDto update(@PathVariable Long userId,
                                   @PathVariable Long requestId) {
        log.info("Получен запрос PATCH /users/{}/request/{}/", userId, requestId);
        return service.update(userId, requestId);
    }

    @GetMapping
    List<ParticipationRequestDto> getAll(@PathVariable Long userId) {
        log.info("Получен запрос GET /users/{}/requests/", userId);
        return service.getAll(userId);
    }
}
