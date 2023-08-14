package ru.practicum.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends NoSuchElementException {
    public CommentNotFoundException(Long id) {
        super("Комментарий с Id " + id + " не найден.");
    }
}
