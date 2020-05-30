package ru.dariedu.terminals.monitoring.api.statuses;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.dariedu.terminals.monitoring.api.common.Auditable;
import ru.dariedu.terminals.monitoring.api.terminals.Terminal;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Status extends Auditable {

    @Id
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "terminal_id", nullable = false)
    private Terminal terminal;

    @Column(name = "last_signal_date", nullable = false)
    private Date lastSignalDate;

}
