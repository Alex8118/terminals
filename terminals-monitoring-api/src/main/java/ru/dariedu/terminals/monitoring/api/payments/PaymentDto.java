package ru.dariedu.terminals.monitoring.api.payments;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {

    private Integer id;

    @NotNull
    private Integer terminalId;

    @NotEmpty
    private String externalId;

    @NotNull
    private Long amount;

    @NotNull
    private PaymentStatus status;
}
