package com.saurabh.smartrecruit.security;

import com.saurabh.smartrecruit.entity.Role;
import com.saurabh.smartrecruit.entity.User;
import com.saurabh.smartrecruit.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootstrapUserInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BootstrapUserInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminName;
    private final String adminEmail;
    private final String adminPassword;
    private final String hrName;
    private final String hrEmail;
    private final String hrPassword;

    public BootstrapUserInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.bootstrap.admin.name:}") String adminName,
            @Value("${app.bootstrap.admin.email:}") String adminEmail,
            @Value("${app.bootstrap.admin.password:}") String adminPassword,
            @Value("${app.bootstrap.hr.name:}") String hrName,
            @Value("${app.bootstrap.hr.email:}") String hrEmail,
            @Value("${app.bootstrap.hr.password:}") String hrPassword
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.hrName = hrName;
        this.hrEmail = hrEmail;
        this.hrPassword = hrPassword;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedUser(adminName, adminEmail, adminPassword, Role.ADMIN);
        seedUser(hrName, hrEmail, hrPassword, Role.HR);
    }

    private void seedUser(String name, String email, String password, Role role) {
        if (!hasText(email) || !hasText(password)) {
            return;
        }

        if (userRepository.existsByEmail(email.trim())) {
            log.info("Bootstrap {} account already exists for {}", role.name(), email.trim());
            return;
        }

        User user = new User();
        user.setName(hasText(name) ? name.trim() : getDefaultName(role));
        user.setEmail(email.trim());
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);
        log.info("Created bootstrap {} account for {}", role.name(), email.trim());
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String getDefaultName(Role role) {
        return switch (role) {
            case ADMIN -> "Administrator";
            case HR -> "HR Manager";
            default -> role.name();
        };
    }
}
