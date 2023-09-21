package ru.practicum.ewm.main.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewm.main.api.error.ErrorResponse;
import ru.practicum.ewm.main.util.exception.AlreadyExistsException;
import ru.practicum.ewm.main.util.exception.BadRequestException;
import ru.practicum.ewm.main.util.exception.NotFoundException;
import ru.practicum.ewm.util.aspect.log.ToLog;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@ToLog
public class ErrorHandler {

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final RuntimeException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .reason(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({AlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExists(final RuntimeException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .reason(HttpStatus.CONFLICT.getReasonPhrase())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({ValidationException.class,
            BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(final RuntimeException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .reason(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(final Exception e) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .reason(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
