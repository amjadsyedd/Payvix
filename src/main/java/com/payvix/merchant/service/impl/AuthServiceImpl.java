package com.payvix.merchant.service.impl;

import com.payvix.common.enums.MerchantStatus;
import com.payvix.common.enums.UserRole;
import com.payvix.common.exception.DuplicateResourceException;
import com.payvix.merchant.dto.request.MerchantSignupRequest;
import com.payvix.merchant.dto.response.MerchantResponse;
import com.payvix.merchant.entity.AppUser;
import com.payvix.merchant.entity.Merchant;
import com.payvix.merchant.repository.AppUserRepository;
import com.payvix.merchant.repository.MerchantRepository;
import com.payvix.merchant.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository appUserRepository;
    private final MerchantRepository merchantRepository;


    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {
        if (merchantRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL","Merchant with email already exists: " + request.email());
        }



        //here we are creating a new merchant and app user and saving them to the database abd every variable or filed or column of the entity is set as .name(request.name()) and so on and the status of the merchant is set to PENDING_KYC and then the merchant is saved to the database and then the app user is created and saved to the database and then the merchant response is returned to the client
        Merchant merchant = Merchant.builder()
                .name(request.name())
                .email(request.email())
                .businessName(request.businessName())
                .businessType(request.businessType())
                .status(MerchantStatus.PENDING_KYC)
                .build();
        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(request.password()) //passwordEncoder.encode(request.password())
                .role(UserRole.OWNER)
                .build();
        appUserRepository.save(appUser);


        return new MerchantResponse(
                merchant.getId(),
                merchant.getName(),
                merchant.getEmail(),
                merchant.getBusinessName(),
                merchant.getBusinessType(),
                merchant.getStatus()
        );
    }


}



