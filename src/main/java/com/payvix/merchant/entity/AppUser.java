package com.payvix.merchant.entity;


import com.payvix.common.enums.UserRole;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
//import org.jspecify.annotations.Nullable;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "app_user") //,indexes = {@Index(name = "idx_app_user_merchant_id", columnList = "merchant_id")}
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    //first word goes to class name, second word goes to variable name & Lazy loading is used to fetch the merchant details only when needed, not every time an AppUser is fetched. if eager loading was used, it would fetch the merchant details every time an AppUser is fetched, which could be inefficient if the merchant details are not always needed.
    @ManyToOne(fetch = FetchType.LAZY)
//    making the merchant_id column in the app_user table a foreign key that references the id column in the merchant table. This establishes a relationship between the AppUser and Merchant entities, allowing you to easily access the associated Merchant for a given AppUser.
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(
//                new SimpleGrantedAuthority("ROLE_"+role)
//        );
//    }
//
//    @Override
//    public @Nullable String getPassword() {
//        return passwordHash;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
}
