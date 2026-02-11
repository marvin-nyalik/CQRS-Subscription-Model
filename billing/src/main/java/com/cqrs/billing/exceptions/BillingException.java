package com.cqrs.billing.exceptions;

public sealed class BillingException extends RuntimeException
    permits PaymentDeclinedException,
            DuplicateChargeException,
            BillingServiceUnavailableException {
    protected BillingException(String message){
        super(message);
    }
}
