package ru.dariedu.terminals.monitoring.api.security;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dariedu.terminals.monitoring.api.users.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final List<GrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority("User")); //TODO this line is ignored
        return userService.getUserByEmail(username)
                .map(
                        user ->
                                new org.springframework.security.core.userdetails.User(
                                        String.valueOf(user.getId()),
                                        user.getPasswordHash(),
                                        authorities
                                )
                )
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
