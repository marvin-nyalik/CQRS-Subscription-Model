package com.cqrs.subscription.infrastructure;

import com.cqrs.subscription.exception.DuplicateChargeInSubscription;
import com.cqrs.subscription.exception.PaymentDeclinedInSubscription;
import com.cqrs.subscription.exception.SubscriptionBillingException;
import com.cqrs.subscription.infrastructure.billing.dto.BillingServiceResponse;
import com.cqrs.subscription.infrastructure.billing.dto.ChargeRequest;
import com.cqrs.subscription.infrastructure.billing.dto.PaymentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(BillingServiceResponse.class)
                                .defaultIfEmpty(new BillingServiceResponse("UNKNOWN", "Client error"))
                                .flatMap(error ->
                                {
                                    int status = response.statusCode().value();
        if (status == 409) {
            return Mono.error(new DuplicateChargeInSubscription(error.message()));
        } else if (status == 422 || status == 400) {
            return Mono.error(new PaymentDeclinedInSubscription(error.message()));
        } else {
            return Mono.error(new PaymentDeclinedInSubscription("Invalid billing request"));
        }
    })
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(
                                new SubscriptionBillingException(
                                        "Billing service unavailable"
                                )
                        )
                )
                .bodyToMono(PaymentResult.class)
                .block();
    }
}
