package com.cqrs.subscription.service;

import com.cqrs.subscription.dto.CreateSubscriptionDTO;
import com.cqrs.subscription.exception.SubscriptionNotFoundException;
import com.cqrs.subscription.infrastructure.BillingClient;
import com.cqrs.subscription.infrastructure.billing.dto.ChargeRequest;
import com.cqrs.subscription.infrastructure.billing.dto.PaymentResult;
import com.cqrs.subscription.infrastructure.billing.dto.PaymentStatus;
import com.cqrs.subscription.model.Subscription;
import com.cqrs.subscription.model.SubscriptionStatus;
import com.cqrs.subscription.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubscriptionService {
    private final SubscriptionRepository repository;
    private final BillingClient billingClient;

    public SubscriptionService(SubscriptionRepository repository, BillingClient billingClient){
        this.repository = repository;
        this.billingClient = billingClient;
    }

    public Subscription createSubscription(CreateSubscriptionDTO dto){
        repository.findByUserIdAndStatus(
                dto.getUserId(), SubscriptionStatus.ACTIVE
                )
                .ifPresent(s -> { throw new IllegalStateException("User has an active subscription."); });

        Subscription subscription =
                Subscription.create(dto.getUserId(), dto.getPlanCode());

        subscription.markPending();
        repository.save(subscription);

        PaymentResult result = billingClient.charge(
                new ChargeRequest(
                        subscription.getUserId(),
                        subscription.getId(),
                        subscription.getPlanCode(),
                        String.valueOf(subscription.getId()),
                        12.5,
                        "KES"
                )
        );

        if (result.status() == PaymentStatus.PAID) {
            subscription.activate();
        } else if (result.status() == PaymentStatus.PENDING) {
            subscription.markPending();
        } else {
            subscription.markFailed();
        }

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
