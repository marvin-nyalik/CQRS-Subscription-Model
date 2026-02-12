package com.cqrs.billing.exceptions;

import com.cqrs.billing.dto.BillingErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PaymentDeclinedException.class)
    public ResponseEntity<BillingErrorResponse> handleDeclined(PaymentDeclinedException ex){
        return ResponseEntity
                .status(402)
                .body(new BillingErrorResponse("PAYMENT DECLINED", ex.getMessage()));
    }

    public ResponseEntity<BillingErrorResponse> handleDuplicateCharge(DuplicateChargeException ex){
        return ResponseEntity
                .status(409)
                .body(new BillingErrorResponse("DUPLICATE CHARGE", ex.getMessage()));
    }

    public ResponseEntity<BillingErrorResponse> handleUnavailableService(BillingServiceUnavailableException ex){
        return ResponseEntity
                .status(503)
                .body(new BillingErrorResponse("SERVICE UNAVAILABLE", ex.getMessage()));
    }
}
