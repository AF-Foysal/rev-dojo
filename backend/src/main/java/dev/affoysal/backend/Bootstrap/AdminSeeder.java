package dev.affoysal.backend.Bootstrap;

import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import dev.affoysal.backend.DTO.RegisterUserDTO;
import dev.affoysal.backend.Entity.Role;
import dev.affoysal.backend.Entity.RoleEnum;
import dev.affoysal.backend.Entity.User;
import dev.affoysal.backend.Repository.RoleRepository;
import dev.affoysal.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createAdministrator();
        createSampleStudent();
        createSampleInstructor();
    }

    private void createAdministrator() {
        RegisterUserDTO userDTO = new RegisterUserDTO();
        userDTO.setFirstName("Admin");
        userDTO.setLastName("User");
        userDTO.setEmail("admin@example.com");

        Optional<User> adminUserOpt = userRepository.findByEmail(userDTO.getEmail());
        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN).get();
        if (adminUserOpt.isPresent()) {
            return; // Admin user must not exist
        }

        User adminUser = new User();
        adminUser.setFirstName(userDTO.getFirstName());
        adminUser.setLastName(userDTO.getLastName());
        adminUser.setEmail(userDTO.getEmail());
        adminUser.setPassword("admin123"); // In a real application, ensure to hash the
        adminUser.setRole(adminRole);
        adminUser.setVerified(true);
        adminUser.setActive(true);
        userRepository.save(adminUser);
    }

    private void createSampleStudent() {
        RegisterUserDTO userDTO = new RegisterUserDTO();
        userDTO.setFirstName("Sample");
        userDTO.setLastName("Student");
        userDTO.setEmail("student@example.com");

        Optional<User> studentUserOpt = userRepository.findByEmail(userDTO.getEmail());
        Role studentRole = roleRepository.findByName(RoleEnum.STUDENT).get();

        if (studentUserOpt.isPresent()) {
            return; // Student user must not exist
        }

        User studentUser = new User();
        studentUser.setFirstName(userDTO.getFirstName());
        studentUser.setLastName(userDTO.getLastName());
        studentUser.setEmail(userDTO.getEmail());
        studentUser.setPassword("student123"); // In a real application, ensure to hash the
        studentUser.setRole(studentRole);
        studentUser.setVerified(true);
        studentUser.setActive(true);
        userRepository.save(studentUser);
    }

    private void createSampleInstructor() {
        RegisterUserDTO userDTO = new RegisterUserDTO();
        userDTO.setFirstName("Sample");
        userDTO.setLastName("Instructor");
        userDTO.setEmail("instructor@example.com");

        Optional<User> instructorUserOpt = userRepository.findByEmail(userDTO.getEmail());
        Role instructorRole = roleRepository.findByName(RoleEnum.INSTRUCTOR).get();

        if (instructorUserOpt.isPresent()) {
            return; // Instructor user must not exist
        }

        User instructorUser = new User();
        instructorUser.setFirstName(userDTO.getFirstName());
        instructorUser.setLastName(userDTO.getLastName());
        instructorUser.setEmail(userDTO.getEmail());
        instructorUser.setPassword("instructor123"); // In a real application, ensure to hash the
        instructorUser.setRole(instructorRole);
        instructorUser.setVerified(true);
        instructorUser.setActive(true);
        userRepository.save(instructorUser);
    }
}
