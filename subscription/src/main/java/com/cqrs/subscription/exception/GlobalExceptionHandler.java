package com.cqrs.subscription.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<?> notFound(SubscriptionNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of ("timestamp", Instant.now(),
                        "Message", ex.getMessage())
        );
    }

    @ExceptionHandler(ActiveSubscriptionExistsException.class)
    public ResponseEntity<?> conflictFound(ActiveSubscriptionExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of ("timestamp", Instant.now(),
                        "Message", ex.getMessage())
        );
    }
}
