package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.CommentRequestByParams;
import ru.practicum.main.comment.service.PublicCommentService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
public class PublicCommentController {

    private final PublicCommentService service;

    @GetMapping("/{commentId}")
    CommentDto get(@PathVariable Long commentId) {
        log.info("Получен запрос GET /comments/{}/", commentId);
        return service.get(commentId);
    }

    @GetMapping
    List<CommentDto> getAll(CommentRequestByParams request) {
        log.info("Получен запрос GET /comments/");
        return service.getAll(request);
    }
}
