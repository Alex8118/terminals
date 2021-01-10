package ru.dariedu.terminals.monitoring.api.users;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrentUserService currentUserService;

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getTokenById(int id) { //TO DO name is wrong. getUserById is the same, DataMockedConf doesn't work with user checking
        User user = currentUserService.getUserSecurity().get();
        if (id == user.getId() || user.isAdmin()) {
            return userRepository.findById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access is denied");
        }
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
