package com.cqrs.billing.model;

import com.cqrs.billing.types.PaymentStatus;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(
        name = "payments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "idempotencyKey")
        }
)
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID subscriptionId;

    @Column(nullable = false)
    private String planCode;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false, unique = true)
    private String idempotencyKey;

    private String failureReason;

    @Version
    private Long version;

    protected Payment() {}

    public static Payment initiate(
            UUID userId,
            UUID subscriptionId,
            String planCode,
            String idempotencyKey
    ) {
        Payment p = new Payment();
        p.userId = userId;
        p.subscriptionId = subscriptionId;
        p.planCode = planCode;
        p.idempotencyKey = idempotencyKey;
        p.status = PaymentStatus.PENDING;
        return p;
    }

    public void markPaid() {
        this.status = PaymentStatus.PAID;
    }

    public void markFailed(String reason) {
        this.status = PaymentStatus.FAILED;
        this.failureReason = reason;
    }

    public void refund() {
        if (this.status != PaymentStatus.PAID) {
            throw new IllegalStateException(
                    "Only PAID payments can be refunded"
            );
        }

        this.status = PaymentStatus.REFUNDED;
    }
}

