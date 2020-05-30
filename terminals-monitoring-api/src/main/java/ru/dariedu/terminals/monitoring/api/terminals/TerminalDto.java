package ru.dariedu.terminals.monitoring.api.terminals;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;
import ru.dariedu.terminals.monitoring.api.statuses.StatusDto;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TerminalDto {

    private Integer id;

    @NotNull
    private Integer ownerId;

    private String name;

    @NotEmpty
    private String city;

    @NotEmpty
    private String street;

    @NotEmpty
    private String house;

    @NotEmpty
    private String macAddress;

    private StatusDto status;

}
