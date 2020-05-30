package ru.dariedu.terminals.monitoring.api.exceptions;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

@Setter
@Getter
@Builder
public class ApiError {

    public Date timestamp;

    public String error;

    public String details;

}
