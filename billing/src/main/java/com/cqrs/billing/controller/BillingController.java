package com.cqrs.billing.controller;

import com.cqrs.billing.dto.ChargeRequest;
import com.cqrs.billing.model.Payment;
import com.cqrs.billing.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billing/payments")
public class BillingController {
    private final BillingService billingService;

    public BillingController(BillingService service){
        this.billingService = service;
    }

    @PostMapping("")
    public Payment charge(
            @RequestHeader("Idempotency-Key") String key,
            @Valid @RequestBody ChargeRequest request
            ){
        Payment pay = billingService.charge(
                request.getUserId(),
                request.getSubscriptionId(),
                request.getPlanCode(),
                request.getIdempotencyKey()
        );

        return pay;
    }
}
