package ru.dariedu.terminals.monitoring.api.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.dariedu.terminals.monitoring.api.common.Auditable;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "encrypted_api_token", nullable = false)
    private String encryptedApiToken;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "locked", nullable = false)
    private Boolean locked;

    public boolean isAdmin() {
        return role.equals("ROLE_ADMIN");
    }

}
