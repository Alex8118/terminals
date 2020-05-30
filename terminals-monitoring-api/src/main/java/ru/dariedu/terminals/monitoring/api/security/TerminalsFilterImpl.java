package ru.dariedu.terminals.monitoring.api.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.dariedu.terminals.monitoring.api.users.UserService;

@Component
@Profile("!demo")
public class TerminalsFilterImpl extends OncePerRequestFilter  implements TerminalsFilter {

    @Value("${security.acceptable.delay.hours}")
    private int acceptableDelayHours;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestSignature = request.getHeader("Authorization");
        String ownerId = request.getParameter("ownerId");
        String timestamp = request.getParameter("timestamp");
        long timestamp2 = Long.parseLong(timestamp);
        Boolean actStamp = DateUtils.isTimestampOutdated(timestamp2, acceptableDelayHours);
        if (requestSignature != null && ownerId != null && actStamp) {
            boolean signatureMatches = userService.getUserById(Integer.parseInt(ownerId))
                    .map(user -> HashUtil.getSignature(user.getEncryptedApiToken(), ownerId, timestamp))
                    .map(signature -> signature.equals(requestSignature))
                    .orElse(false);
            if (signatureMatches) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "The auth details are not valid.");
            }
        } else {
            if (!actStamp) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The timestamp was created more then 12 hours ago.");
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The auth details are not present.");
            }
        }
    }

}
