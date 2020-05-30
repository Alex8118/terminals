package ru.dariedu.terminals.monitoring.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.dariedu.terminals.monitoring.api.users.UserService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Profile("demo")
public class TerminalsFilterMockedImpl extends OncePerRequestFilter implements TerminalsFilter{

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestSignature = request.getHeader("Authorization");
        String ownerId = request.getParameter("ownerId");
        String timestamp = request.getParameter("timestamp");
        if (requestSignature != null && ownerId != null && timestamp != null) {
            if (requestSignature.equals("123456789")) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "The auth details are not valid.");
            }
        } else {
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The auth details are not present.");
            }
        }

}
