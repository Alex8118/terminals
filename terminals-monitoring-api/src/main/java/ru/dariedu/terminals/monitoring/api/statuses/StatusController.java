package ru.dariedu.terminals.monitoring.api.statuses;

import static ru.dariedu.terminals.monitoring.api.common.Constants.API_PATH;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.Instant;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(API_PATH + "/statuses")
@Api(tags = {"Statuses"})
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private StatusMapper statusMapper;

    @Autowired
    private StatusHistoryMapper statusHistoryMapper;

    @PutMapping
    @ApiOperation(value = "Add first terminal's status or update current terminal's status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public StatusDto update(
            @ApiParam(value = "Status object store in database table", required = true)
            @Valid @RequestBody StatusDto statusDto
    ) {
        return statusService.getStatusByTerminalId(statusDto.getTerminalId())
                .map(status -> status.setLastSignalDate(Date.from(Instant.now())))
                .map(status -> statusService.saveStatus(status))
                .map(updateStatus -> statusMapper.fromStatus(updateStatus))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such status by terminal id"));
    }

    @Transactional(readOnly = true)
    @GetMapping("/history")
    @ApiOperation(value = "View list history statuses by terminal")
    public Page<StatusHistoryDto> findHistoryByTerminalId(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            @ApiParam(value = "Terminal id from which terminal's status will retrieve", required = true)
            @RequestParam int terminalId
    ) {
        return statusService.getStatusHistoryByTerminalId(terminalId, pageable)
                .map(statusHistory -> statusHistoryMapper.fromStatus(statusHistory));
    }

}
