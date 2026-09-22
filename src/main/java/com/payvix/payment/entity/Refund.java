package com.payvix.payment.entity;

import com.payvix.common.entity.Money;
import com.payvix.common.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "refund")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
                  //extends BaseEntity
public class Refund  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
//join colmn represent foriegn key in the refund table which is referencing the primary key of the payment table. It establishes a relationship between the two entities, allowing you to associate a refund with a specific payment. The name attribute specifies the name of the foreign key column in the refund table, and nullable = false indicates that this column cannot be null, meaning that every refund must be associated with a payment.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(nullable = false)
    private UUID merchantId;

    @Embedded
    private Money amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus status = RefundStatus.PENDING;

    @Column(length = 100)
    private String bankReference;

    @Column(length = 100)
    private String errorCode;

    @Column(length = 500)
    private String errorDescription;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> notes;

    private LocalDateTime processedAt;
}

