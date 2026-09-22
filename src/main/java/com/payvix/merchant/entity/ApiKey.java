package com.payvix.merchant.entity;

import com.payvix.common.enums.Environment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "api_key",
        indexes = {
                @Index(name = "idx_api_key_merchant_env", columnList = "merchant_id, environment, enabled")
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//Creating API key in the database for a specific merchant and environment. The API key consists of a unique key ID and a hashed secret key, which is used for authentication when making API requests. The API key can be enabled or disabled, and it can also have a grace period for rotation to enhance security.
//for example merchant is zara API key is created for zara A in production environment, with a unique key ID and a hashed secret key. The API key is enabled and can be used for authentication when making API requests to the production environment. If the API key is rotated, there will be a grace period during which both the old and new keys can be used for authentication.
//for bulk merchants they use this api key in there backend and rzaorpay knows which merchant is using this api key and which environment it is being used in, so it can route the request to the correct merchant and environment.
                   //extends BaseEntity
public class ApiKey  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false, length = 50, unique = true)
    private String keyId;

    @Column(nullable = false, length = 200)
    private String keySecretHash;

    @Column(length = 200)
    private String previousKeySecretHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Environment environment;

    @Column(nullable = false)
    @Builder.Default
    private boolean enabled = true;


    private java.time.LocalDateTime lastUsedAt;
//    Api Rotation is used to enhance security by periodically changing the API keys. This reduces the risk of unauthorized access in case an API key is compromised. The grace period allows for a smooth transition, giving clients time to update their systems with the new API key before the old one becomes invalid.
    private java.time.LocalDateTime rotatedAt;
    private java.time.LocalDateTime gracePeriodExpiresAt;

    public boolean isInGracePeriod() {
        return gracePeriodExpiresAt != null && LocalDateTime.now().isBefore(gracePeriodExpiresAt);
    }
}

