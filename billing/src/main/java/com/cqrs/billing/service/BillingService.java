package com.cqrs.billing.service;

import com.cqrs.billing.exceptions.BillingServiceUnavailableException;
import com.cqrs.billing.exceptions.DuplicateChargeException;
import com.cqrs.billing.exceptions.PaymentDeclinedException;
import com.cqrs.billing.model.Payment;
import com.cqrs.billing.repository.PaymentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class BillingService {
    private final PaymentRepository repository;

    public BillingService(PaymentRepository repo){
        this.repository = repo;
    }

    @Transactional
    public Payment charge (
            UUID userId,
            UUID subscriptionId,
            String planCode,
            String idempotencyKey
    ){
        repository.findByIdempotencyKey(idempotencyKey)
                .ifPresent(p -> {
                    throw new DuplicateChargeException("Duplicate charge on payment");
                });
        return createAndProcessPayment(userId, subscriptionId, planCode, idempotencyKey);
    }

    private Payment createAndProcessPayment(
            UUID userID,
            UUID subscriptionID,
            String planCode,
            String key
    ) {
        Payment payment = Payment.initiate(userID, subscriptionID, planCode, key);
        repository.save(payment);
        simulateExternalCall(payment);
        return payment;
    }

    private void simulateExternalCall(Payment pay){
        double r = Math.random();
        if(r < 0.2){
            pay.markFailed("Provider Unavailable");
            throw new BillingServiceUnavailableException(
                    "Payment provider is unavailable"
            );
        }

        if(r < 0.4){
            pay.markFailed("Payment declined");
            throw new PaymentDeclinedException("Payment declined by provider");
        }

        pay.markPaid();
    }

    public void refund(UUID paymentId) {
        Payment p = repository.findById(paymentId)
                .orElseThrow();

        p.refund();

        repository.save(p);
    }

}
