package ru.practicum.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompilationNotFoundException extends NoSuchElementException {
    public CompilationNotFoundException(Long id) {
        super("Подборка с Id " + id + " не найдена.");
    }
}
