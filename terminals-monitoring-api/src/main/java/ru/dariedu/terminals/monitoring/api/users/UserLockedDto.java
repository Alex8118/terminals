package ru.dariedu.terminals.monitoring.api.users;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserLockedDto {

    private boolean locked;

}
