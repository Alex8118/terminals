package ru.dariedu.terminals.monitoring.api.statuses;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusHistoryDto {

    private Integer id;

    @NotNull
    private Integer terminalId;

    private Date lastSignalDate;

}
