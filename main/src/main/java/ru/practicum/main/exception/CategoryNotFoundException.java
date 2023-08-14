package ru.practicum.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends NoSuchElementException {
    public CategoryNotFoundException(Long id) {
        super("Категория с Id " + id + " не найдена.");
    }
}
