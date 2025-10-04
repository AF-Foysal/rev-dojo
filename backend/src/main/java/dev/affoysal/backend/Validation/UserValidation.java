package dev.affoysal.backend.Validation;

import dev.affoysal.backend.Entity.UserEntity;
import dev.affoysal.backend.Exception.ApiException;

public class UserValidation {

    public static void verifyAccountStatus(UserEntity userEntity) {
        if (!userEntity.isVerified()) {
            throw new ApiException("Account is not verified");
        }
    }

}
