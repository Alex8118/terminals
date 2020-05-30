package ru.dariedu.terminals.monitoring.api.terminals;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.dariedu.terminals.monitoring.api.common.Auditable;
import ru.dariedu.terminals.monitoring.api.statuses.Status;
import ru.dariedu.terminals.monitoring.api.users.User;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Terminal extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "house", nullable = false)
    private String house;

    @Column(name = "mac_address", nullable = false)
    private String macAddress;

    @OneToOne(mappedBy = "terminal", cascade = CascadeType.ALL)
    private Status status;

}
