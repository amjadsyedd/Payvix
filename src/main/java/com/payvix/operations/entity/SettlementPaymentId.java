package com.payvix.operations.entity;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
//Reason for cretaing this calss is we have settlementId and paymentID as primary key and forign key in the settlement_payment table. So we need to create a composite primary key class to represent the primary key of the settlement_payment table. This class will be used as an embedded id in the SettlementPayment entity.
public class SettlementPaymentId {

    private UUID settlementId;

    private UUID paymentId;
}

