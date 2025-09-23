package dev.affoysal.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.affoysal.backend.Entity.Role;
import dev.affoysal.backend.Entity.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(RoleEnum name);
}
