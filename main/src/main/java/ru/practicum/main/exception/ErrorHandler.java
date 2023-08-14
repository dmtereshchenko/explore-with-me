package ru.practicum.main.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ApiError;
import ru.practicum.Constant;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ActionNotAvailableException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleActionNotAvailableException(ActionNotAvailableException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(), "Действие невозможно.", HttpStatus.FORBIDDEN.getReasonPhrase(), LocalDateTime.now().format(Constant.FORMATTER));
    }

    @ExceptionHandler({CategoryNotFoundException.class, CompilationNotFoundException.class, EventNotFoundException.class,
            RequestNotFoundException.class, UserNotFoundException.class, CommentNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NoSuchElementException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(), "Данные не найдены.", HttpStatus.NOT_FOUND.getReasonPhrase(), LocalDateTime.now().format(Constant.FORMATTER));
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidDataException(InvalidDataException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(), "Данные некорректны.", HttpStatus.BAD_REQUEST.getReasonPhrase(), LocalDateTime.now().format(Constant.FORMATTER));
    }

    @ExceptionHandler(SomethingWentWrongException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleSomethingWentWrongException(SomethingWentWrongException e) {
        log.warn(e.getMessage());
        return new ApiError(e.getMessage(), "Что-то пошло не так.", HttpStatus.CONFLICT.getReasonPhrase(), LocalDateTime.now().format(Constant.FORMATTER));
    }
}
