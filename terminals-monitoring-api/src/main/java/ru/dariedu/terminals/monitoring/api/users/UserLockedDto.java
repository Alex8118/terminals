package ru.dariedu.terminals.monitoring.api.users;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLockedDto {

    private boolean locked;

}
