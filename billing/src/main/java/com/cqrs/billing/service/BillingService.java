package com.cqrs.billing.service;

import com.cqrs.billing.model.Payment;
import com.cqrs.billing.repository.PaymentRepository;

import java.util.UUID;

public class BillingService {
    private final PaymentRepository repository;

    public BillingService(PaymentRepository repo){
        this.repository = repo;
    }

    private Payment createAndProcess(
            UUID userID,
            UUID subscriptionID,
            String planCode,
            String key
    ){
        Payment p = Payment.initiate(userID, subscriptionID, planCode, key);
        repository.save(p);
        return p;
    }

    private void simulateExternalCall(Payment pay){
        double r = Math.random();
        if(r < 0.3){
            pay.markFailed("Provider Unavailable");
            return;
        }

        if(r < 0.6){
            try{
                Thread.sleep(4000);
            } catch (InterruptedException e) {

            }
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
