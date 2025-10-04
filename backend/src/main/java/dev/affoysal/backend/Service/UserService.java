package dev.affoysal.backend.Service;

import dev.affoysal.backend.DTO.User;
import dev.affoysal.backend.Entity.CredentialEntity;
import dev.affoysal.backend.Entity.RoleEntity;

public interface UserService {
    void createUser(String firstName, String lastName, String email, String password);

    RoleEntity getRoleName(String name);

    void verifyAccountToken(String token);

    User getUserByEmail(String email);

    CredentialEntity getUserCredentialById(Long id);
}
