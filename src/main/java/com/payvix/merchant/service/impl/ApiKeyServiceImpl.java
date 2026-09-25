package com.payvix.merchant.service.impl;

import com.payvix.common.exception.ResourceNotFoundException;
import com.payvix.merchant.dto.request.CreateApiKeyRequest;
import com.payvix.merchant.dto.response.ApiKeyCreateResponse;
import com.payvix.merchant.dto.response.MerchantResponse;
import com.payvix.merchant.entity.ApiKey;
import com.payvix.merchant.entity.Merchant;
import com.payvix.merchant.repository.ApiKeyRepository;
import com.payvix.merchant.repository.MerchantRepository;
import com.payvix.merchant.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;


    @Override
    public ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request) {
         Merchant merchant = merchantRepository.findById(merchantId)
                 .orElseThrow(()-> new ResourceNotFoundException("Merchant", merchantId));

       String keyId = "pv"+request.environment().name().toUpperCase()+"big_Random_Secerfghdfghet";
        String rawSecret = "big_Random_Secerfghdfghet";

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret)
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyCreateResponse(apiKey.getId(), keyId, rawSecret, request.environment());
    }

}

