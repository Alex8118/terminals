package ru.dariedu.terminals.monitoring.api.payments;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.dariedu.terminals.monitoring.api.common.Auditable;
import ru.dariedu.terminals.monitoring.api.terminals.Terminal;
import javax.persistence.*;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Payment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "terminal_id", nullable = false)
    private Terminal terminal;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "status", nullable = false)
    private PaymentStatus status;
}
