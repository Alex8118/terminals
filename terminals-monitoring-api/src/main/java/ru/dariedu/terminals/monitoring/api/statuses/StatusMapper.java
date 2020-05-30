package ru.dariedu.terminals.monitoring.api.statuses;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    StatusDto fromStatus(Status source);

    @InheritInverseConfiguration
    Status toStatus(StatusDto target);

}
