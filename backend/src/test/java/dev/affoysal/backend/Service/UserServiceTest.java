package dev.affoysal.backend.Service;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.affoysal.backend.Entity.CredentialEntity;
import dev.affoysal.backend.Entity.RoleEntity;
import dev.affoysal.backend.Entity.UserEntity;
import dev.affoysal.backend.Enumeration.Authority;
import dev.affoysal.backend.Repository.CredentialRepository;
import dev.affoysal.backend.Repository.UserRepository;
import dev.affoysal.backend.Service.Impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CredentialRepository credentialRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("Test find user by email")
    public void getUserByEmailTest() {
        // Arrange - Given
        var userEntity = new UserEntity();
        userEntity.setFirstName("TestMan");
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        Instant now = Instant.now();
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);

        var roleEntity = new RoleEntity("ADMIN", Authority.ADMIN);
        userEntity.setRole(roleEntity);

        var credentialEntity = new CredentialEntity();
        credentialEntity.setUpdatedAt(now);
        credentialEntity.setPassword("password");
        credentialEntity.setUserEntity(userEntity);

        when(userRepository.findByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(userEntity));
        lenient().when(credentialRepository.getCredentialByUserEntityId(1L)).thenReturn(Optional.of(credentialEntity));

        // Act - When
        var userByUserId = userServiceImpl.getUserByEmail("test@example.com");

        // Assert - Then
        Assertions.assertThat(userByUserId.getFirstName()).isEqualTo(userEntity.getFirstName());
        Assertions.assertThat(userByUserId.getEmail()).isEqualTo("test@example.com");
    }
}
