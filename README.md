#TV Subscription Service

## Overview

This project is a **microservices-based Subscription application** built with **Spring Boot and Spring Cloud**. Its purpose is to demonstrate how core microservices infrastructure components fit together in practice, rather than to model a full production billing system.

The system focuses on **service discovery, routing, and inter-service communication**, using clean boundaries and production-aligned patterns.

---

## Architecture

The application is composed of the following services:

### 1. Eureka Server

* Acts as the **Service Discovery Registry**
* All services register themselves here
* Enables dynamic discovery and client-side load balancing

### 2. API Gateway

* Single entry point into the system
* Routes requests to downstream services using service IDs (`lb://`)
* Central place for cross-cutting concerns (auth, logging, rate limiting)

### 3. Subscription Service

* Manages subscription plans and user subscriptions
* Exposes REST APIs for creating and querying subscriptions
* Registers with Eureka

### 4. User Service (optional / next step)

* Manages users/customers
* Used by Subscription Service for ownership and validation

```
Client
  ↓
API Gateway
  ↓ (service discovery)
Subscription Service
  ↓
Eureka Server
```

---

## Tech Stack

* Java 17
* Spring Boot 3.x
* Spring Cloud 2023.x
* Spring Cloud Netflix Eureka
* Spring Cloud Gateway
* Maven

---

## Key Concepts Demonstrated

* Service Discovery with Eureka
* API Gateway routing using service IDs
* Decoupled microservices
* Environment-agnostic configuration
* Clean separation of infrastructure vs business logic

---

## Running the Application

### Start order (important)

1. Eureka Server
2. API Gateway
3. Subscription Service (and any other services)

### Eureka Dashboard

```
http://localhost:8761
```

Use the dashboard to verify that services are registered and healthy.

---

## Example Gateway Route

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: subscription-service
          uri: lb://SUBSCRIPTION-SERVICE
          predicates:
            - Path=/subscriptions/**
```

---

## Project Goals

This project is intentionally:

* Small
* Understandable
* Infrastructure-focused

It is meant to answer:

> "How do Spring Cloud components actually work together ?"

---

## Future Enhancements

* Authentication & Authorization (JWT at Gateway)
* Inter-service calls with WebClient
* Centralized configuration (Spring Cloud Config)
* Resilience (Resilience4j)
* Messaging (Kafka) for subscription events
* Observability (Micrometer, Zipkin)

---

## Notes

This project prioritizes **clarity over completeness**. Each component is kept minimal so the role of every moving part is explicit and easy to reason about.
