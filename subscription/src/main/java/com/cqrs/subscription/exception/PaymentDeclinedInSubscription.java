package com.cqrs.subscription.exception;

public class PaymentDeclinedInSubscription extends SubscriptionBillingException {
    public PaymentDeclinedInSubscription(String message) { super(message); }
}
