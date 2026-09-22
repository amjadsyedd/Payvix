package com.payvix.vault.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_token")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
                      //extends BaseEntity
public class CardToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(nullable = false, length = 50, unique = true)
    //we store the card info in VaultCard.java so that pci dss complaince is limited to this vault only and all othere micoservces in our system they just interact ith this token and nobbody in our system or outside can know the real pan information of the card. This is a security measure to protect sensitive card data and ensure compliance with industry standards.
    private String token;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "vault_card_id", nullable = false)
    private VaultCard vaultCard;

    private UUID customer;

    @Column(nullable = false)
    private UUID merchant;

    private LocalDateTime revokedAt;

}

