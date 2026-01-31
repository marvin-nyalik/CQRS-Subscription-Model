package com.cqrs.subscription.exception;

public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException () {
        super("Subscription Not Found!");
    }
}
