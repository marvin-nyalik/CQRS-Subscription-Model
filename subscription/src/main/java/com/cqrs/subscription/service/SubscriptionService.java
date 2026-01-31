package com.cqrs.subscription.service;

import com.cqrs.subscription.dto.CreateSubscriptionDTO;
import com.cqrs.subscription.exception.SubscriptionNotFoundException;
import com.cqrs.subscription.model.Subscription;
import com.cqrs.subscription.model.SubscriptionStatus;
import com.cqrs.subscription.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;

import java.util.UUID;

public class SubscriptionService {
    private final SubscriptionRepository repository;

    public SubscriptionService(SubscriptionRepository repository){
        this.repository = repository;
    }

    public Subscription createSubscription(CreateSubscriptionDTO dto){
        repository.findByUserIdAndStatus(dto.getUserId(), SubscriptionStatus.ACTIVE)
                .ifPresent(s -> { throw new IllegalStateException("User has an active subscription."); });
        Subscription subscription =
                Subscription.create(dto.getUserId(), dto.getPlanCode());
        subscription.activate();
        return repository.save(subscription);
    }

    public Subscription get(UUID id){
        return repository.findById(id).
                orElseThrow(SubscriptionNotFoundException::new);
    }

    @Transactional
    public Subscription cancel(UUID id) {
        Subscription sub = get(id);
        sub.cancel();
        return repository.save(sub);
    }
}
