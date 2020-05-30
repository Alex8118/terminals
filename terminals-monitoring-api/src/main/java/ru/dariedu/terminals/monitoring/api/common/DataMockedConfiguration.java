package ru.dariedu.terminals.monitoring.api.common;

import java.time.Instant;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.dariedu.terminals.monitoring.api.payments.PaymentController;
import ru.dariedu.terminals.monitoring.api.payments.PaymentDto;
import ru.dariedu.terminals.monitoring.api.payments.PaymentStatus;
import ru.dariedu.terminals.monitoring.api.statuses.StatusController;
import ru.dariedu.terminals.monitoring.api.statuses.StatusDto;
import ru.dariedu.terminals.monitoring.api.terminals.TerminalController;
import ru.dariedu.terminals.monitoring.api.terminals.TerminalDto;
import ru.dariedu.terminals.monitoring.api.users.UserController;
import ru.dariedu.terminals.monitoring.api.users.UserDto;
import ru.dariedu.terminals.monitoring.api.users.UserRepository;

@Component
@Profile("demo")
public class DataMockedConfiguration {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TerminalController terminalController;

    @Autowired
    private StatusController statusController;

    @Autowired
    private PaymentController paymentController;

    @Value("${mock.users.fat.name}")
    private String fatUserName;

    @Value("${mock.users.fat.email}")
    private String fatUserEmail;

    @Value("${mock.users.fat.fatness}")
    private int fatUserFatness;

    @Value("${mock.users.password}")
    private String mockUsersPassword;

    @Value("${mock.users.count}")
    private int mockUsersCount;

    @Value("${mock.users.fatness}")
    private int mockUsersFatness;

    @PostConstruct
    private void generateMockData() {
        for (int i = 1; i < mockUsersCount; i++) {
            createUser(i, mockUsersFatness);
        }
        createUser(fatUserName, fatUserEmail, fatUserFatness);
        userRepository.unlockAll();
    }

    private void createUser(int i, int fatness) {
        createUser("Fond" + i, i + "@gmail.com", fatness);
    }

    private void createUser(String name, String email, int fatness) {
        Date date = Date.from(Instant.now());
        UserDto user = UserDto.builder()
                .name(name)
                .email(email)
                .locked(false)
                .password(mockUsersPassword)
                .build();
        UserDto userCreated = userController.create(user);
        createTerminal(date, userCreated.getId(), fatness);
    }

    private void createTerminal(Date date, int userId, int fatness) {
        for (int i = 1; i < fatness; i++) {
            TerminalDto terminalDto = TerminalDto.builder()
                    .ownerId(userId)
                    .name("Fond_" + i + "_Terminal")
                    .city("Default")
                    .street("Broad")
                    .house("34" + i)
                    .macAddress(userId + "1234" + i)
                    .build();
            TerminalDto terminalCreate = terminalController.create(terminalDto);
            createMockedStatus(terminalCreate.getId(), date, fatness);
            createMockedPayment(terminalCreate.getId(), fatness);
        }
    }

    private void createMockedPayment(int terminalId, int fatness) {
        for (int i = 1; i < fatness; i++) {
            PaymentDto terminalPayment = PaymentDto.builder()
                    .terminalId(terminalId)
                    .externalId(terminalId + "123" + i)
                    .amount(12345L)
                    .status(PaymentStatus.POST)
                    .build();
            paymentController.create(terminalPayment);
        }
    }

    private void createMockedStatus(int terminalId, Date date, int fatness) {
        for (int i = 1; i < fatness; i++) {
            StatusDto terminalStatus = StatusDto.builder()
                    .terminalId(terminalId)
                    .lastSignalDate(date)
                    .build();
            statusController.update(terminalStatus);
        }
    }

}
