package ru.dariedu.terminals.monitoring.api.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dariedu.terminals.monitoring.api.security.TokenUtils;

@Mapper(componentModel = "spring", imports = TokenUtils.class)
@Service
public abstract class UserMapper {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public abstract UserDto fromUser(User source);

    @Mapping(target = "passwordHash", expression = "java(passwordEncoder.encode(source.getPassword()))")
    @Mapping(target = "encryptedApiToken", expression = "java(TokenUtils.generateApiToken())")
    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "locked", constant = "true")
    public abstract User toUser(UserDto source);

}
