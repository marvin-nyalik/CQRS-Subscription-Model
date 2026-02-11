package com.cqrs.subscription.exception;

public class DuplicateChargeInSubscription extends SubscriptionBillingException {
    public DuplicateChargeInSubscription(String message) { super(message); }
}
