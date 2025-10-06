package dev.affoysal.backend.validation;

import dev.affoysal.backend.entity.UserEntity;
import dev.affoysal.backend.exception.ApiException;

public class UserValidation {

    public static void verifyAccountStatus(UserEntity userEntity) {
        if (!userEntity.isEnabled()) {
            throw new ApiException("Account is not verified");
        }
        if (!userEntity.isAccountNonExpired()) {
            throw new ApiException("Account is expired");
        }
        if (!userEntity.isAccountNonLocked()) {
            throw new ApiException("Account is locked");
        }
    }
}