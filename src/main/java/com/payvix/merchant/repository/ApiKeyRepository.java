package com.payvix.merchant.repository;

import com.payvix.merchant.dto.request.CreateApiKeyRequest;
import com.payvix.merchant.dto.response.ApiKeyCreateResponse;
import com.payvix.merchant.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {
}
