package ru.dariedu.terminals.monitoring.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenUtils {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public String generateApiToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String generateJwtToken(String subject, String jwtSecret, long tokenExpirationTime) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(tokenExpirationTime))))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

}
