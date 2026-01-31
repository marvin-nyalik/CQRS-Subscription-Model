package com.cqrs.subscription.controller;

import com.cqrs.subscription.dto.CreateSubscriptionDTO;
import com.cqrs.subscription.model.Subscription;
import com.cqrs.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    public Subscription get(UUID id){
        return service.get(id);
    }

    @PostMapping("")
    public Subscription create(@Valid @RequestBody CreateSubscriptionDTO request){
        return  service.createSubscription(request);
    }

    @PostMapping("/cancel/{id}")
    public Subscription cancelSubscription(@PathVariable UUID id){
        return service.cancel(id);
    }
}
