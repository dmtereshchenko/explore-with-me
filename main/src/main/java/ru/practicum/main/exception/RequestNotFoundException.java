package ru.practicum.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequestNotFoundException extends NoSuchElementException {

    public RequestNotFoundException(Long id) {
        super("Запрос на участие с Id " + id + " не найден.");
    }
}
