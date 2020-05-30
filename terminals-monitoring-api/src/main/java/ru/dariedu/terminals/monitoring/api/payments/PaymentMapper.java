package ru.dariedu.terminals.monitoring.api.payments;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.dariedu.terminals.monitoring.api.statuses.StatusMapper;

@Mapper(componentModel = "spring", uses = StatusMapper.class)
public interface PaymentMapper {

    @Mapping(source = "terminal.id", target = "terminalId")
    PaymentDto fromPayment(Payment source);

    @InheritInverseConfiguration
    Payment toPayment(PaymentDto target);

}
