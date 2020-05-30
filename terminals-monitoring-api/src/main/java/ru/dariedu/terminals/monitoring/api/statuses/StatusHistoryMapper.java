package ru.dariedu.terminals.monitoring.api.statuses;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatusHistoryMapper {

    @Mapping(source = "terminal.id", target = "terminalId")
    @Mapping(target = "lastSignalDate", source = "createdDate")
    StatusHistoryDto fromStatus(StatusHistory source);

    @InheritInverseConfiguration
    StatusHistory toStatus(StatusHistoryDto target);

}
