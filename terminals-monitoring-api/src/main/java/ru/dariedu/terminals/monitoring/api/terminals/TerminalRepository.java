package ru.dariedu.terminals.monitoring.api.terminals;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Integer> {

    Page<Terminal> findByOwnerId(int ownerId, Pageable pageable);

    Optional<Terminal> findById(int id);

    Terminal save(Terminal terminal);

}
