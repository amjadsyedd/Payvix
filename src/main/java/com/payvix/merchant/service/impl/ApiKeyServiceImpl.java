package com.payvix.merchant.service.impl;

import com.payvix.common.exception.ResourceNotFoundException;
import com.payvix.common.util.RandomizerUtil;
import com.payvix.merchant.dto.request.CreateApiKeyRequest;
import com.payvix.merchant.dto.response.ApiKeyCreateResponse;
import com.payvix.merchant.dto.response.ApiKeyResponse;
import com.payvix.merchant.dto.response.MerchantResponse;
import com.payvix.merchant.entity.ApiKey;
import com.payvix.merchant.entity.Merchant;
import com.payvix.merchant.repository.ApiKeyRepository;
import com.payvix.merchant.repository.MerchantRepository;
import com.payvix.merchant.service.ApiKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;


    @Override
    @Transactional
    public ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request) {
         Merchant merchant = merchantRepository.findById(merchantId)
                 .orElseThrow(()-> new ResourceNotFoundException("Merchant", merchantId));

       String keyId = "pv"+request.environment().name().toUpperCase()+ RandomizerUtil.randomBase64(24);
        String rawSecret = RandomizerUtil.randomBase64(40);

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret)
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(), keyId, rawSecret, request.environment());
    }

    @Override
    public List<ApiKeyResponse> listByMerchant(UUID merchantId) {
        return apiKeyRepository.findByMerchant_id(merchantId)
                .stream()
                .map(apiKey -> new ApiKeyResponse(
                        apiKey.getId(),
                        apiKey.getKeyId(),
                        apiKey.getEnvironment(),
                        apiKey.isEnabled(),
                        apiKey.getLastUsedAt(),
                        null
                )).toList();
    }


    // Revoke the API key by marking it as disabled instead of deleting it.
    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID keyId) {
         ApiKey key = apiKeyRepository.findById(keyId)
                 .filter(k-> k.getMerchant().getId().equals(merchantId))
                 .orElseThrow(()-> new ResourceNotFoundException("ApiKey", keyId));

         key.setEnabled(false);
         apiKeyRepository.save(key);

    }

    @Override
    public @Nullable ApiKeyCreateResponse rotate(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(k -> k.getMerchant().getId().equals(merchantId))
                .orElseThrow(() -> new ResourceNotFoundException("ApiKey", keyId));

//        if(!apiKey.isEnabled()) throw new RuntimeException("Cannot rotate a disabled key");

        String newRawSecret = RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newRawSecret);
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiresAt(LocalDateTime.now().plusHours(24));
        apiKey = apiKeyRepository.save(apiKey);

//        apiKeyCache.evict(apiKey.getKeyId());

        return new ApiKeyCreateResponse(apiKey.getId(), apiKey.getKeyId(),
                newRawSecret, apiKey.getEnvironment());
    }


}

