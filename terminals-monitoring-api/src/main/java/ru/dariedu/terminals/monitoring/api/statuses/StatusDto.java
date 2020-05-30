package ru.dariedu.terminals.monitoring.api.statuses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusDto {

    @NotNull
    @JsonProperty(access = Access.WRITE_ONLY)
    private Integer terminalId;

    private Date lastSignalDate;

}
