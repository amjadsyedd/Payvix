package com.payvix.merchant.dto.request;

import com.payvix.common.enums.BusinessType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//Signup DTO request for merchant signup
//used record to make it immutable and thread safe and also to reduce boilerplate code or writing anotations as @getters @setters @toString @equals @hashcode so we get all this benefits in record than class
public record MerchantSignupRequest(

        @NotNull(message = "Name is required")
        @Size(max = 50, message = "Name should not be more than 50 characters long")
        String name,

        @Email
        @NotNull(message = "Email is required")
        String email,

        @NotNull(message = "Password is required")
        @Size(min = 8, message = "Password should be at least 8 characters long")
        String password,

        @Size(max = 50, message = "Business name should not be more than 50 characters long")
        String businessName,

        BusinessType businessType
) {
}

