package ru.dariedu.terminals.monitoring.api.common;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.dariedu.terminals.monitoring.api.security.TokenUtils;
import ru.dariedu.terminals.monitoring.api.users.User;
import ru.dariedu.terminals.monitoring.api.users.UserRepository;

@Component
public class DataInitConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${users.admin.password}")
    private String adminPassword;

    @Value("${users.admin.email}")
    private String adminEmail;

    @PostConstruct
    private void postConstruct() {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            String passwordHash = passwordEncoder.encode(adminPassword);
            String encryptedApiToken = TokenUtils.generateApiToken();
            User admin = User.builder()
                    .email(adminEmail)
                    .encryptedApiToken(encryptedApiToken)
                    .passwordHash(passwordHash)
                    .locked(false)
                    .name("admin")
                    .role("ROLE_ADMIN")
                    .build();
            userRepository.save(admin);
        }
    }

}

