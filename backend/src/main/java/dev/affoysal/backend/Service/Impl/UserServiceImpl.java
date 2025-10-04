package dev.affoysal.backend.Service.Impl;

import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.affoysal.backend.DTO.User;
import dev.affoysal.backend.Entity.ConfirmationEntity;
import dev.affoysal.backend.Entity.CredentialEntity;
import dev.affoysal.backend.Entity.RoleEntity;
import dev.affoysal.backend.Entity.UserEntity;
import dev.affoysal.backend.Enumeration.Authority;
import dev.affoysal.backend.Enumeration.EventType;
import dev.affoysal.backend.Event.UserEvent;
import dev.affoysal.backend.Exception.ApiException;
import dev.affoysal.backend.Repository.ConfirmationRepository;
import dev.affoysal.backend.Repository.CredentialRepository;
import dev.affoysal.backend.Repository.RoleRepository;
import dev.affoysal.backend.Repository.UserRepository;
import dev.affoysal.backend.Service.UserService;
import dev.affoysal.backend.Utility.UserUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;
    private final BCryptPasswordEncoder encoder;
    private final ApplicationEventPublisher publisher;

    @Override
    public void createUser(String firstName, String lastName, String email, String password) {
        var userEntity = userRepository.save(createNewUser(firstName, lastName, email));
        var credentialEntity = new CredentialEntity(encoder.encode(password), userEntity);
        credentialRepository.save(credentialEntity);
        var confirmationEntity = new ConfirmationEntity(userEntity);
        confirmationRepository.save(confirmationEntity);
        publisher.publishEvent(
                new UserEvent(userEntity, EventType.REGISTRATION, Map.of("token", confirmationEntity.getToken())));
    }

    @Override
    public RoleEntity getRoleName(String name) {
        var role = roleRepository.findByNameIgnoreCase(name);
        return role.orElseThrow(() -> new ApiException("Role not found"));
    }

    private UserEntity createNewUser(String firstName, String lastName, String email) {
        var role = getRoleName(Authority.STUDENT.name());
        return UserUtils.createUserEntity(firstName, lastName, email, role);
    }

    @Override
    public void verifyAccountToken(String token) {
        var confirmationEntity = getUserConfirmation(token);
        UserEntity userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        userEntity.setVerified(true);
        userRepository.save(userEntity);
        confirmationRepository.delete(confirmationEntity);
    }

    private UserEntity getUserEntityByEmail(String email) {
        var userByEmail = userRepository.findByEmailIgnoreCase(email);
        return userByEmail.orElseThrow(() -> new ApiException("User not found."));
    }

    private ConfirmationEntity getUserConfirmation(String token) {
        return confirmationRepository.findByToken(token).orElseThrow(() -> new ApiException("Confirmation not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        UserEntity userEntity = getUserEntityByEmail(email);
        return UserUtils.fromUserEntity(userEntity, userEntity.getRole());
    }

    @Override
    public CredentialEntity getUserCredentialById(Long id) {
        var credentialById = credentialRepository.getCredentialByUserEntityId(id);
        return credentialById.orElseThrow(() -> new ApiException("Unable to find credential"));
    }
}
