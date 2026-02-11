package com.cqrs.billing.exceptions;

public final class BillingServiceUnavailableException extends BillingException {

    public BillingServiceUnavailableException(String message){
        super(message);
    }
}
