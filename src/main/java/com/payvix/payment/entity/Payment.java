package com.payvix.payment.entity;

import com.payvix.common.entity.Money;
import com.payvix.common.enums.PaymentMethod;
import com.payvix.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "payment", indexes = {
        @Index(name = "idx_payment_order_id", columnList = "order_id"),
        @Index(name = "idx_payment_merchant_id", columnList = "merchant_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
                   //extends BaseEntity
public class Payment  {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderRecord order;

    @Column(nullable = false)
    private UUID merchantId;

//    marking it as embedded means that the fields of the Money class will be mapped to columns in the Payment table, rather than creating a separate table for Money. This allows for a more straightforward representation of the amount associated with a payment, as it can be stored directly within the Payment entity without requiring additional joins or relationships.
    @Embedded
    private Money amount;

    @Column(nullable = false, length = 100)
    private String idempotencyKey;

    // marking as enumerated means that the PaymentStatus enum will be stored as a string in the database, rather than as an ordinal (integer) value. This makes the database more readable and maintainable, as the status values will be stored as their string representations (e.g., "PENDING", "COMPLETED") instead of numeric indices.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentStatus status;

    @Column(nullable = false)
    private PaymentMethod method;

    // using Map here because it allows for a flexible structure to store various details related to the payment method, such as card information, bank account details, or any other relevant data. The JSONB column type in the database can efficiently store this map as a JSON object, enabling easy retrieval and manipulation of the payment method details without requiring a rigid schema.
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "method_details", columnDefinition = "jsonb")
    private Map<String, Object> methodDetails;

    @Column(length = 100)
    private String bankReference;

    @Column(length = 100)
    private String processorReference;

    @Column(length = 100)
    private String errorCode;

    @Column(length = 255)
    private String errorDescription;

    private LocalDateTime authorizedAt;

    private LocalDateTime capturedAt;

    private LocalDateTime failedAt;

    private LocalDateTime refundedAt;

    private LocalDateTime settledAt;
}

