package ru.dariedu.terminals.monitoring.api.security;

import static ru.dariedu.terminals.monitoring.api.common.Constants.HEADER_STRING;
import static ru.dariedu.terminals.monitoring.api.common.Constants.TOKEN_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.server.ResponseStatusException;
import ru.dariedu.terminals.monitoring.api.users.User;
import ru.dariedu.terminals.monitoring.api.users.UserDto;
import ru.dariedu.terminals.monitoring.api.users.UserRepository;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static String jwtSecret;
    private static long tokenExpirationTime;

    public JWTAuthenticationFilter(
            String url,
            AuthenticationManager authManager,
            String jwtSecret,
            long tokenExpirationTime,
            UserRepository userRepository
    ) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        JWTAuthenticationFilter.jwtSecret = jwtSecret;
        JWTAuthenticationFilter.tokenExpirationTime = tokenExpirationTime;
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws AuthenticationException, IOException {
        UserDto userDto = new ObjectMapper().readValue(req.getInputStream(), UserDto.class);
        var email = userDto.getEmail();
        //TODO rollback after extracting "locked" check into UserDetailsService
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn’t exist"));
        boolean locked = user.getLocked();

        if (locked != true) {
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getEmail(),
                            userDto.getPassword(),
                            Collections.emptyList()
                    )
            );
        } else {
            //TODO fix - this is ignored now, 401 is thrown instead
            throw new LockedException("User is locked");
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth
    ) {
        addAuthentication(res, auth.getName());
    }

    static void addAuthentication(HttpServletResponse res, String id) {
        String jwtToken = TokenUtils.generateJwtToken(id, jwtSecret, tokenExpirationTime);
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwtToken);
    }

}
