package ru.dariedu.terminals.monitoring.api.terminals;

import static ru.dariedu.terminals.monitoring.api.common.Constants.API_PATH;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.dariedu.terminals.monitoring.api.users.UserService;

@RestController
@RequestMapping(API_PATH + "/terminals")
@Api(tags = {"Terminals"})
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @Autowired
    private TerminalMapper terminalMapper;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @GetMapping
    @ApiOperation(value = "View list terminal by ownerId")
    public Page<TerminalDto> find(
            @PageableDefault(size = 10, page = 0) Pageable pageable,
            @ApiParam(value = "User id from which user's terminal will retrieve", required = true)
            @RequestParam Integer ownerId
    ) {
        return terminalService.findByOwnerId(ownerId, pageable)
                .map(t -> terminalMapper.fromTerminal(t));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "View terminal's info by terminalId")
    public TerminalDto getById(
            @ApiParam(value = "Terminal id from which terminal will retrieve", required = true)
            @PathVariable int id
    ) {
        return terminalService.findTerminalById(id)
                .map(t -> terminalMapper.fromTerminal(t))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such terminal id"));
    }

    @PostMapping
    @ApiOperation(value = "Add new terminal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 422, message = "Validation error")
    })
    public TerminalDto create(
            @ApiParam(value = "Terminal object store in database table", required = true)
            @Valid @RequestBody TerminalDto terminalDto
    ) {
        return userService.getUserById(terminalDto.getOwnerId())
                .map(user -> terminalMapper.toTerminal(terminalDto).setOwner(user))
                .map(terminal -> terminalService.createTerminal(terminal))
                .map(savedTerminal -> terminalMapper.fromTerminal(savedTerminal))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such owner id"));
    }

}
