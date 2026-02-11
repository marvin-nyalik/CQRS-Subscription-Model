package com.cqrs.billing.exceptions;

public final class PaymentDeclinedException extends BillingException {
    public PaymentDeclinedException(String message){
        super(message);
    }
}
