package com.cqrs.subscription.repository;

import com.cqrs.subscription.model.Subscription;
import com.cqrs.subscription.model.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional <Subscription> findByUserIdAndStatus(UUID userId, SubscriptionStatus status);
}
