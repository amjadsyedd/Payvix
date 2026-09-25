package com.payvix.merchant.dto.request;


import com.payvix.common.enums.Environment;

public record CreateApiKeyRequest(
        Environment environment
) {
}