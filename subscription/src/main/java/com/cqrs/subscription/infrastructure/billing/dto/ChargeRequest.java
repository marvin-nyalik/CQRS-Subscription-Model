package com.cqrs.subscription.infrastructure.billing.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record ChargeRequest(
        UUID userId,
        UUID subscriptionId,
        String planCode,
        String idempotencyKey,
        BigDecimal amount,
        String currency
) {
    public ChargeRequest {
        Objects.requireNonNull(userId, "userId");
        Objects.requireNonNull(subscriptionId, "subscriptionId");
        Objects.requireNonNull(planCode, "planCode");
        Objects.requireNonNull(idempotencyKey, "idempotencyKey");
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
    }
}

