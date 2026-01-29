package com.cqrs.subscription.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(
        name = "subscriptions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_plan",
                        columnNames = {"user_id", "plan_code"}
                )
        }
)
public class Subscription {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull(message = "User ID is required")
    @Column(nullable = false)
    private UUID userId;

    @NotBlank(message = "Plan code must not be blank")
    @Size(max = 50, message = "Plan code must be at most 50 characters")
    @Column(nullable = false, length = 50)
    private String planCode;

    @NotNull(message = "Subscription status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    @NotNull(message = "Start date is required")
    private Instant startDate;

    @Future(message = "End date must be in the future")
    private Instant endDate;

    @NotNull
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @NotNull
    @Column(nullable = false)
    private Instant updatedAt;

    @Version
    private long version;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public void activate() {
        if (this.status != SubscriptionStatus.PENDING) {
            throw new IllegalStateException(
                    "Only PENDING subscriptions can be activated"
            );
        }
        this.status = SubscriptionStatus.ACTIVE;
        this.startDate = Instant.now();
    }

    public void cancel() {
        if (this.status != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Only ACTIVE subscriptions can be cancelled"
            );
        }
        this.status = SubscriptionStatus.CANCELLED;
        this.endDate = Instant.now();
    }

    public void expire() {
        if (this.status != SubscriptionStatus.ACTIVE) {
            throw new IllegalStateException(
                    "Only ACTIVE subscriptions can expire"
            );
        }
        this.status = SubscriptionStatus.EXPIRED;
        this.endDate = Instant.now();
    }

    public boolean isActive() {
        return this.status == SubscriptionStatus.ACTIVE
                && (endDate == null || Instant.now().isBefore(endDate));
    }

    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        if (endDate == null) {
            return true;
        }
        return endDate.isAfter(startDate);
    }
}
