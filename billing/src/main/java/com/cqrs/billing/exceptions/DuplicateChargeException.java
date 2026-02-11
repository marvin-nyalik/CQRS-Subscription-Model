package com.cqrs.billing.exceptions;

public final class DuplicateChargeException extends BillingException {
    public DuplicateChargeException(String message){
        super(message);
    }
}
