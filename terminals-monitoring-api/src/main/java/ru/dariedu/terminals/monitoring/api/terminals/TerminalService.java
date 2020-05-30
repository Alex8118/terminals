package ru.dariedu.terminals.monitoring.api.terminals;

import java.util.Date;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dariedu.terminals.monitoring.api.statuses.Status;
import ru.dariedu.terminals.monitoring.api.users.CurrentUserService;
import ru.dariedu.terminals.monitoring.api.users.User;

@Service
public class TerminalService {

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private CurrentUserService currentUserService;

    public Page<Terminal> findByOwnerId(int ownerId, Pageable pageable) {
        User user = currentUserService.getUserSecurity().get();
        if (ownerId == user.getId() || user.isAdmin()) {
            return terminalRepository.findByOwnerId(ownerId, pageable);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access is denied");
        }
    }

    public Optional<Terminal> findTerminalById(int id) {
        return terminalRepository.findById(id);
    }

    @Transactional
    public Terminal createTerminal(Terminal terminal) {
        Date date = Date.from(Instant.now());
        Status status = Status.builder()
                .terminal(terminal)
                .lastSignalDate(date)
                .build();
        return terminalRepository.save(terminal.setStatus(status));
    }

}
