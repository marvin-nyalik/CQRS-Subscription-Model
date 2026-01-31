package com.cqrs.subscription.exception;

public class ActiveSubscriptionExistsException extends RuntimeException {
    public ActiveSubscriptionExistsException (){
        super("User already has an active subscription!");
    }
}
