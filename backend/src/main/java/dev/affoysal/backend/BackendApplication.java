package dev.affoysal.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import dev.affoysal.backend.Domain.RequestContext;
import dev.affoysal.backend.Entity.RoleEntity;
import dev.affoysal.backend.Enumeration.Authority;
import dev.affoysal.backend.Repository.RoleRepository;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
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
					role.setAuthority(authority);
					roleRepository.save(role);
				}
			}

			RequestContext.start();
		};
	}

}
