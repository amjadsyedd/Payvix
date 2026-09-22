package com.payvix.merchant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "merchant_webhook_config",
        indexes = {
                @Index(name = "idx_webhook_merchant_id", columnList = "merchant_id, enabled")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
                                  //extends BaseEntity
public class MerchantWebhookConfig  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @Column(nullable = false, length = 500)
    private String targetUrl; //www.zara.com/webhook/success
//webhooksecret is a secret key that is used to verify the authenticity of the webhook requests sent to the targetUrl. It is typically a random string that is known only to the sender and receiver of the webhook. When a webhook request is received, the receiver can use the webhookSecret to validate that the request came from a trusted source and has not been tampered with.
    @Column(length = 255)
    private String webhookSecret;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(length = 255)
    private String eventTypes;
    // Comma-separated list of event types to subscribe to

    public boolean isSubscribedTo(String eventType) {
        if (eventTypes == null || eventTypes.isBlank()) {
            return true;
        }
        for (String type : eventTypes.split(",")) {
            String trimmed = type.trim();
            if (trimmed.equalsIgnoreCase("ALL") || trimmed.equalsIgnoreCase(eventType)) {
                return true;
            }
        }
        return false;
    }
}

