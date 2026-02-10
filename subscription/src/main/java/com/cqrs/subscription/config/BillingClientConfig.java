package com.cqrs.subscription.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BillingClientConfig {

    @Bean
    @LoadBalanced
    WebClient billingWebClient(WebClient.Builder builder){
        return builder
                .baseUrl("http://BILLING-SERVICE")
                .build();
    }
}
