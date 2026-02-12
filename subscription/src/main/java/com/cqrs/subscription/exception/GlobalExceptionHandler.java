package com.cqrs.subscription.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<?> notFound(SubscriptionNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "status", 404,
                        "error", "NOT_FOUND",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(ActiveSubscriptionExistsException.class)
    public ResponseEntity<?> conflictFound(ActiveSubscriptionExistsException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "status", 409,
                        "error", "CONFLICT",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(PaymentDeclinedInSubscription.class)
    public ResponseEntity<?> handlePaymentDeclined(PaymentDeclinedInSubscription ex) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                .body(Map.of("status","PAYMENT_DECLINED","mesage", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateChargeInSubscription.class)
    public ResponseEntity<?> handleDuplicate(DuplicateChargeInSubscription ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("status","PAYMENT_DECLINED","mesage", ex.getMessage()));
    }

    @ExceptionHandler(SubscriptionBillingException.class)
    public ResponseEntity<?> handleBillingServiceUnavailable(SubscriptionBillingException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("status","PAYMENT_DECLINED","mesage", ex.getMessage()));}
}
