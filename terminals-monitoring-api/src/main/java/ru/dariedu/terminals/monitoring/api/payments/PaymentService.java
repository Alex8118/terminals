package ru.dariedu.terminals.monitoring.api.payments;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dariedu.terminals.monitoring.api.terminals.TerminalService;
import ru.dariedu.terminals.monitoring.api.users.CurrentUserService;
import ru.dariedu.terminals.monitoring.api.users.User;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private TerminalService terminalService;

    public Page<Payment> findByTerminalId(int terminalId, Pageable pageable) {
        User user = currentUserService.getUserSecurity().get();
        int ownerId = terminalService.findTerminalById(terminalId).get().getOwner().getId();
        if (ownerId == user.getId() || user.isAdmin()) {
            return paymentRepository.findByTerminalId(terminalId, pageable);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access is denied");
        }
    }

    public Optional<Payment> findPaymentById(int id) {
        User user = currentUserService.getUserSecurity().get();
        if (user.isAdmin()) {
            return paymentRepository.findById(id);
        } else {
            return paymentRepository
                    .findById(id)
                    .filter(payment -> payment.getTerminal().getOwner().getId() == user.getId());
        }
    }

    @Transactional
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

}
