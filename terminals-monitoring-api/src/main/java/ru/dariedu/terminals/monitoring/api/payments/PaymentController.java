package ru.dariedu.terminals.monitoring.api.payments;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.dariedu.terminals.monitoring.api.terminals.TerminalService;
import javax.validation.Valid;
import static ru.dariedu.terminals.monitoring.api.common.Constants.API_PATH;

@RestController
@RequestMapping(API_PATH + "/payments")
@Api(tags = {"Payments"})
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private TerminalService terminalService;

    @Transactional(readOnly = true)
    @GetMapping
    @ApiOperation(value = "List payments by terminal id")
    public Page<PaymentDto> find(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            @ApiParam(value = "Terminal id from which terminal's payment will retrieve", required = true)
            @RequestParam Integer terminalId
    ) {
        return paymentService.findByTerminalId(terminalId, pageable)
                .map(p -> paymentMapper.fromPayment(p));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "View payments's info by paymentId")
    public PaymentDto getById(
            @ApiParam(value = "Payment id from which payment will retrieve", required = true)
            @PathVariable int id
    ) {
        return paymentService.findPaymentById(id)
                .map(p -> paymentMapper.fromPayment(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such payment id"));
    }

    @PostMapping
    @ApiOperation(value = "Add new payment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public PaymentDto create(
            @ApiParam(value = "Payment object store in database table", required = true)
            @Valid @RequestBody PaymentDto paymentDto
    ) {
        return terminalService.findTerminalById(paymentDto.getTerminalId())
                .map(terminal -> paymentMapper.toPayment(paymentDto).setTerminal(terminal))
                .map(payment -> paymentService.createPayment(payment))
                .map(savedPayment -> paymentMapper.fromPayment(savedPayment))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such terminal id"));
    }

}
