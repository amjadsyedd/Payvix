package com.payvix.merchant.dto.response;

import com.payvix.common.enums.BusinessType;
import com.payvix.common.enums.MerchantStatus;
import lombok.Data;

import java.util.UUID;
//signUp DTO response for merchant signup
public record MerchantResponse(
        UUID id,
        String name,
        String email,
        String businessName,
         BusinessType businessType,
        MerchantStatus merchantStatus
) {
}