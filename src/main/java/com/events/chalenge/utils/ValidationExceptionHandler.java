package com.events.chalenge.utils;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleValidationException(ConstraintViolationException exception) {
        String errorMessage = new ArrayList<>(exception.getConstraintViolations()).get(0).getMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }
}