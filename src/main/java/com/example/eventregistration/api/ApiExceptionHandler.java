package com.example.eventregistration.api;

import com.example.eventregistration.service.RegistrationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new TreeMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request",
                "Request validation failed", fieldErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUnreadableRequest() {
        return new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Bad Request",
                "Request body is malformed or contains an invalid value", Map.of());
    }

    @ExceptionHandler(RegistrationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(RegistrationNotFoundException exception) {
        return new ApiError(Instant.now(), HttpStatus.NOT_FOUND.value(), "Not Found",
                exception.getMessage(), Map.of());
    }
}