package com.cqrs.subscription.infrastructure;

import com.cqrs.subscription.infrastructure.billing.dto.ChargeRequest;
import com.cqrs.subscription.infrastructure.billing.dto.PaymentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class BillingClient {
    private final WebClient billingWebClient;

    public PaymentResult charge(ChargeRequest request){
        return billingWebClient
                .post()
                .uri("/billing/payments")
                .header("Idempotency-Key", request.idempotencyKey())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResult.class)
                .block();

    }
}
