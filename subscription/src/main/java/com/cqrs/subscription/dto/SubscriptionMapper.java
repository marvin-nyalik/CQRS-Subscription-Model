package com.cqrs.subscription.dto;

import com.cqrs.subscription.model.Subscription;

public class SubscriptionMapper {

    private SubscriptionMapper() {
    }

    public static SubscriptionResponseDTO toDto(Subscription subscription) {
        SubscriptionResponseDTO dto = new SubscriptionResponseDTO();
        dto.setId(subscription.getId());
        dto.setUserId(subscription.getUserId());
        dto.setPlanCode(subscription.getPlanCode());
        dto.setStatus(String.valueOf(subscription.getStatus()));
        dto.setStartDate(subscription.getStartDate());
        dto.setEndDate(subscription.getEndDate());
        return dto;
    }
}
