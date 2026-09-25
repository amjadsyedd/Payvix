package com.payvix.merchant.service;

import com.payvix.merchant.dto.request.MerchantSignupRequest;
import com.payvix.merchant.dto.response.MerchantResponse;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

public interface AuthService {
    MerchantResponse signup(MerchantSignupRequest request);

//    LoginResponse login(LoginRequest request);
}