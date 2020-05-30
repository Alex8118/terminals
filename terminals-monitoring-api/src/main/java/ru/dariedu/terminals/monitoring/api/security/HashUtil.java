package ru.dariedu.terminals.monitoring.api.security;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HashUtil {

    public String getSignature(String... params) {
        String joinedParameters = Stream.of(params).collect(Collectors.joining());
        String sha512hex = Hashing.sha512()
                .hashString(joinedParameters, StandardCharsets.UTF_8)
                .toString();
        String encodedString = Base64.getEncoder().encodeToString(sha512hex.getBytes());
        return encodedString;
    }

}
