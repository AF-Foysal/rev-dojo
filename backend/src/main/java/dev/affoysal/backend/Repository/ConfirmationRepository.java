package dev.affoysal.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.affoysal.backend.Entity.ConfirmationEntity;
import dev.affoysal.backend.Entity.UserEntity;

@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Long> {
    Optional<ConfirmationEntity> findByToken(String token);

    Optional<ConfirmationEntity> findByUserEntity(UserEntity userEntity);
}
