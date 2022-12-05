package com.miguel.rest_api_spring;

import com.miguel.rest_api_spring.domain.AppUser;
import com.miguel.rest_api_spring.domain.Role;
import com.miguel.rest_api_spring.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class RestApiSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
				// EVERYTHING INSIDE THIS BLOCK WILL RUN AFTER THE APP IS INITIALIZED

				userService.saveRole(new Role(
						null,
						"ROLE_USER"
				));
				userService.saveRole(new Role(
						null,
						"ROLE_MANAGER"
				));
			userService.saveRole(new Role(
					null,
					"ROLE_ADMIN"
			));
			userService.saveRole(new Role(
					null,
					"ROLE_SUPER_ADMIN"
			));

			userService.saveUser(new AppUser(null, "kurler", "Miguel", "1234", new ArrayList<>()));

			userService.addRoleToUser("kurler", "ROLE_SUPER_ADMIN");


		};
	}
}
