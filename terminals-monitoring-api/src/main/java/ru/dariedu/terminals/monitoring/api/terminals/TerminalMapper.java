package ru.dariedu.terminals.monitoring.api.terminals;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dariedu.terminals.monitoring.api.statuses.StatusMapper;

@Mapper(componentModel = "spring", uses = StatusMapper.class)
public interface TerminalMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    TerminalDto fromTerminal(Terminal source);

    @InheritInverseConfiguration
    Terminal toTerminal(TerminalDto target);

}
