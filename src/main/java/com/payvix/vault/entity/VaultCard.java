package com.payvix.vault.entity;
import com.payvix.common.enums.CardBrand;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vault_card")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
                      //extends BaseEntity
public class VaultCard  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 4)
    private String lastFour;

    @Column(nullable = false, length = 6)
    private String bin; // first 6 digits of card

    @Column(nullable = false)
    private byte[] encryptedPan;

    @Column(nullable = false)
    //encrypting in byte array format to store the encrypted data securely in the database. This ensures that sensitive information is protected and not stored in plain text.
    //we can store in sting format as well but byte array is more secure and efficient for encryption purposes. and consume less space in the database.
    private byte[] encryptedDek;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardBrand brand; // VISA, RUPAY

    @Column(nullable = false)
    private String expiryMonth;

    @Column(nullable = false)
    private String expiryYear;

    @Column(nullable = false)
    private String cardHolderName;

    private LocalDateTime deletedAt;
}


