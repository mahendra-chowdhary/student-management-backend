
package com.coder.fe_be.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coder.fe_be.entity.Role;
import com.coder.fe_be.entity.User;
import com.coder.fe_be.repository.UserRepository;

@Configuration
public class AdminSeeder {

    @Value("${ADMIN_PASSWORD:}")
    private String adminPassword;

    @Profile("prod")
    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (adminPassword.isBlank()) {
                System.out.println("ADMIN_PASSWORD is not set.");
                return;
            }

            User admin = userRepository.findByUsername("admin")
                    .orElseGet(() -> {
                        User newAdmin = new User();
                        newAdmin.setUsername("admin");
                        newAdmin.setEmail("admin@example.com");
                        return newAdmin;
                    });

            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("ADMIN account created/updated successfully.");
        };
    }
}
