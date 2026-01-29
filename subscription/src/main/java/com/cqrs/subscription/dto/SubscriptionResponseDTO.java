package com.cqrs.subscription.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter

public class SubscriptionResponseDTO {
    public UUID id;
    public UUID userId;
    public String planCode;
    public String status;
    public Instant startDate;
    public Instant endDate;
}
