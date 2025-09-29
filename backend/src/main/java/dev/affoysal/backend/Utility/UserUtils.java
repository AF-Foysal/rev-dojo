package dev.affoysal.backend.Utility;

import java.time.Instant;
import java.util.UUID;

import dev.affoysal.backend.Entity.RoleEntity;
import dev.affoysal.backend.Entity.UserEntity;

public class UserUtils {
    public static UserEntity createUserEntity(String firstName, String lastName, String email, RoleEntity role) {
        return UserEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .lastLoginAttempt(Instant.now())
                .loginAttempts(0)
                .verified(false)
                .role(role)
                .build();
    }
}
