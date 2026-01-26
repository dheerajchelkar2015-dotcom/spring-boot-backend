package com.security.auth_app_backend;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.security.auth_app_backend.config.AppConstants;
import com.security.auth_app_backend.entities.Role;
import com.security.auth_app_backend.repositories.RoleRepository;

@SpringBootApplication
public class AuthAppBackendApplication implements CommandLineRunner{

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthAppBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		roleRepository.findByName("ROLE_"+AppConstants.ADMIN_ROLE)
		.ifPresentOrElse(role -> {
			System.out.println("Admin role already exists"+role.getName());
		}, ()->
		{
			Role role = new Role();
			role.setId(UUID.randomUUID());
			role.setName("ROLE_"+AppConstants.ADMIN_ROLE);
			roleRepository.save(role);
		});

		roleRepository.findByName("ROLE_"+AppConstants.GUEST_ROLE)
		.ifPresentOrElse(role -> {
			System.out.println("Guest role already exists"+role.getName());
		}, ()->
		{
			Role role = new Role();
			role.setId(UUID.randomUUID());
			role.setName("ROLE_"+AppConstants.GUEST_ROLE);
			roleRepository.save(role);
		});

	}

}