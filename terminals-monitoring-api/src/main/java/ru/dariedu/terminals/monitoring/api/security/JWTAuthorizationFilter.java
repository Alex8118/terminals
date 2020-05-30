package ru.dariedu.terminals.monitoring.api.security;

import static ru.dariedu.terminals.monitoring.api.common.Constants.HEADER_STRING;
import static ru.dariedu.terminals.monitoring.api.common.Constants.TOKEN_PREFIX;

import com.sun.istack.Nullable;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.dariedu.terminals.monitoring.api.users.UserRepository;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    private static String jwtSecret;

    public JWTAuthorizationFilter(AuthenticationManager authManager, String jwtSecret, UserRepository userRepository) {
        super(authManager);
        JWTAuthorizationFilter.jwtSecret = jwtSecret;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
        } else {
            Authentication authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        }
    }

    @Nullable
    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null) {
                int user1 = Integer.parseInt(user);
                String role = userRepository.findById(user1).get().getRole(); //TODO without direct call to DB
                return new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority(role)));
            }
        }
        return null;
    }

}
