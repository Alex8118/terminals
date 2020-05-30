package ru.dariedu.terminals.monitoring.api.statuses;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusHistoryRepository statusHistoryRepository;

    public Optional<Status> getStatusByTerminalId(int id) {
        return statusRepository.findById(id);
    }

    public Page<StatusHistory> getStatusHistoryByTerminalId(int terminalId, Pageable pageable) {
        return statusHistoryRepository.findAllByTerminalIdOrderByCreatedDateDesc(terminalId, pageable);
    }

    public Status saveStatus(Status status) {
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setTerminal(status.getTerminal());
        statusHistoryRepository.save(statusHistory);
        return statusRepository.save(status);
    }

}
