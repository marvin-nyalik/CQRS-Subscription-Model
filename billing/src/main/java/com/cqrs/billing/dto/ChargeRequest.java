package com.cqrs.billing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class ChargeRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID subscriptionId;

    @NotBlank
    private String planCode;

    @NotBlank
    private String idempotencyKey;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String currency;

    // getters
    public UUID getUserId() {
        return userId;
    }

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}

