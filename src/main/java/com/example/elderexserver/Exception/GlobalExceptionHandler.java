package com.example.elderexserver.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Not Found", ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
        String message;

        assert ex.getBindingResult() != null;
        if (!ex.getBindingResult().getFieldErrors().isEmpty()) {
            message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        } else if (!ex.getBindingResult().getGlobalErrors().isEmpty()) {
            message = ex.getBindingResult().getGlobalErrors().get(0).getDefaultMessage();
        } else {
            message = "Validation failed";
        }

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(400, "Validation Error", message, LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, "Internal Server Error", ex.getMessage(), LocalDateTime.now()));
    }

}
