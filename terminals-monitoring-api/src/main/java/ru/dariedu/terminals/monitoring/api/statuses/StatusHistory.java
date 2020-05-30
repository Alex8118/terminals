package ru.dariedu.terminals.monitoring.api.statuses;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.dariedu.terminals.monitoring.api.common.Auditable;
import ru.dariedu.terminals.monitoring.api.terminals.Terminal;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class StatusHistory extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "terminal_id", nullable = false)
    private Terminal terminal;

}
