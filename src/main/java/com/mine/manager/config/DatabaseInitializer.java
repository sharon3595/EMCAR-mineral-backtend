package com.mine.manager.config;

import com.mine.manager.security.user.Role;
import com.mine.manager.security.user.User;
import com.mine.manager.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.security.admin.username}")
    private String username;

    @Value("${application.security.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (userRepository.findByUsernameAndStateTrue(username).isPresent()) {
            return;
        }

        log.info("Creando Admin inicial...");
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);
        admin.setName(Role.ADMIN.name());
        admin.setSurname(Role.ADMIN.name());
        admin.setState(true);
        userRepository.save(admin);
        log.info("Admin creado. Recuerde cambiar la contraseña inmediatamente.");
    }
}