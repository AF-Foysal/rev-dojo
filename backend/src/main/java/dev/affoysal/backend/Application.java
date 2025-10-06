package dev.affoysal.backend;

import dev.affoysal.backend.domain.RequestContext;
import dev.affoysal.backend.entity.RoleEntity;
import dev.affoysal.backend.enumeration.Authority;
import dev.affoysal.backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            RequestContext.setUserId(0L);
            Authority[] authorities = Authority.values();

            for (Authority authority : authorities) {
                if (roleRepository.findByNameIgnoreCase(authority.name()).isEmpty()) {
                    RoleEntity role = new RoleEntity();
                    role.setName(authority.name());
                    role.setAuthorities(authority);
                    roleRepository.save(role);
                }
            }

            RequestContext.start();
        };
    }
}