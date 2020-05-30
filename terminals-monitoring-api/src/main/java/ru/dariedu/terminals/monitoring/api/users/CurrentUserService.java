package ru.dariedu.terminals.monitoring.api.users;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserSecurity() {
        Optional<User> userSecurity = Optional.empty();
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                userSecurity = userRepository.findById(
                        Integer.valueOf((String) principal)
                );
            }

        }
        return userSecurity;
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
