package ru.practicum.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ActionNotAvailableException extends RuntimeException {

    public ActionNotAvailableException(String message) {
        super(message);
    }
}
