package ru.dariedu.terminals.monitoring.api.payments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Page<Payment> findByTerminalId(int terminalId, Pageable pageable);

}
