package ru.dariedu.terminals.monitoring.api.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor(staticName = "encryptedApiToken")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserApiTokenDto {

    public String encryptedApiToken;

}
