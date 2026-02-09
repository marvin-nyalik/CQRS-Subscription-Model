package com.cqrs.subscription.infrastructure.billing.dto;

public record PaymentResult(
        PaymentStatus status,
        String paymentId,
        String reason
) {
    public static PaymentResult paid(String paymentId){
        return new PaymentResult(
                PaymentStatus.PAID,
                paymentId,
                null
        );
    }

    public static PaymentResult pending(){
        return new PaymentResult(
                PaymentStatus.PENDING,
                null,
                null
        );
    }

    public static PaymentResult failed(String reason){
        return new PaymentResult(
                PaymentStatus.FAILED,
                null,
                reason
        );
    }

    public boolean isPaid() {
        return status == PaymentStatus.PAID;
    }
}
