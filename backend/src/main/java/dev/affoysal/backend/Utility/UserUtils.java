package dev.affoysal.backend.Utility;

import org.springframework.beans.BeanUtils;

import dev.affoysal.backend.DTO.User;
import dev.affoysal.backend.Entity.RoleEntity;
import dev.affoysal.backend.Entity.UserEntity;

public class UserUtils {
    public static UserEntity createUserEntity(String firstName, String lastName, String email, RoleEntity role) {
        return UserEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .verified(false)
                .role(role)
                .build();
    }

    public static User fromUserEntity(UserEntity userEntity, RoleEntity role) {
        User user = new User();
        BeanUtils.copyProperties(userEntity, user);
        user.setCreatedAt(userEntity.getCreatedAt().toString());
        user.setUpdatedAt(userEntity.getUpdatedAt().toString());
        user.setRole(role.getName());
        user.setAuthorities(role.getAuthority().getValue());
        return user;
    }
}
