package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.service.PrivateCommentService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
public class PrivateCommentController {

    private final PrivateCommentService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CommentDto create(@PathVariable Long userId,
                      @PathVariable Long eventId,
                      @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Получен запрос POST /users/{}/events/{}/comments/", userId, eventId);
        return service.create(userId, eventId, newCommentDto);
    }

    @PatchMapping(path = "/{commentId}")
    CommentDto update(@PathVariable Long userId,
                      @PathVariable Long eventId,
                      @PathVariable Long commentId,
                      @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Получен запрос PATCH /users/{}/events/{}/comments/{}/", userId, eventId, commentId);
        return service.update(userId, commentId, newCommentDto);
    }

    @DeleteMapping(path = "/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long userId,
                @PathVariable Long eventId,
                @PathVariable Long commentId) {
        log.info("Получен запрос DELETE /users/{}/events/{}/comments/{}/", userId, eventId, commentId);
        service.delete(userId, commentId);
    }
}
