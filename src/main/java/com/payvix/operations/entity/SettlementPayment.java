package com.payvix.operations.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settlement_payment")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
                            //extends BaseEntity
public class SettlementPayment {

    @EmbeddedId
    //Reason of writing EmbeddedId is to represent a composite primary key in the SettlementPayment entity. The SettlementPaymentId class is used as the embedded ID, which contains multiple fields that together form the primary key for this entity. This allows for a more complex key structure, enabling the association of settlement payments with specific settlements and other relevant attributes.
    // difference between @Id and @EmbeddedId is that @Id is used for a single primary key field, while @EmbeddedId is used for composite primary keys that consist of multiple fields encapsulated in an embeddable class.
    //difference between @EmbeddedID, @Embedded and @Embeddable is that @EmbeddedId is used to represent a composite primary key in an entity, while @Embedded is used to include an embeddable class as a field within an entity. @Embeddable is used to mark a class as embeddable, indicating that its fields can be embedded in other entities.
    private SettlementPaymentId id;

    //reason of writing MapsID() is to indicate that the settlement field in the SettlementPayment entity is mapped to the settlementId field in the embedded ID (SettlementPaymentId). This establishes a relationship between the SettlementPayment entity and the Settlement entity, allowing you to associate a settlement payment with a specific settlement. The @MapsId annotation ensures that the foreign key column in the SettlementPayment table corresponds to the primary key of the Settlement entity.
    @MapsId("settlementId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "settlement_id", nullable = false)
    private Settlement settlement;
}

