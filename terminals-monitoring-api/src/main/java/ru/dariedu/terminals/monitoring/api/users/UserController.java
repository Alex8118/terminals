package ru.dariedu.terminals.monitoring.api.users;

import static ru.dariedu.terminals.monitoring.api.common.Constants.API_PATH;
import static ru.dariedu.terminals.monitoring.api.common.Constants.HEADER_STRING;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.dariedu.terminals.monitoring.api.security.TokenUtils;

@RestController
@RequestMapping(API_PATH + "/users")
@ControllerAdvice
@Api(tags = {"Users"})
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final CurrentUserService currentUserService;

    @Value("${jwt.token.expiration.minutes}")
    private long tokenExpirationTime;

    @Value("${jwt.token.secret}")
    private String jwtSecret;

    public UserController(UserService userService, UserMapper userMapper, CurrentUserService currentUserService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.currentUserService = currentUserService;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}/impersonate")
    public ResponseEntity<UserDto> impersonate(@PathVariable int id) {
        UserDto userDto = userService.getUserById(id)
                .map(user -> userMapper.fromUser(user))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        String userToken = TokenUtils.generateJwtToken(String.valueOf(id), jwtSecret, tokenExpirationTime);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HEADER_STRING, userToken)
                .body(userDto);
    }

    @GetMapping("/current")
    @ApiOperation(value = "Get current user's info")
    public Object getCurrent() {
        return currentUserService.getUserSecurity()
                .map(user -> userMapper.fromUser(user))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "View user's info by userId (ownerID)")
    public UserDto getById(
            @ApiParam(value = "User id from which user will retrieve", required = true)
            @PathVariable int id
    ) {
        return userService.getUserById(id)
                .map(user -> userMapper.fromUser(user))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/{id}/token")
    public UserApiTokenDto getTokenById(@PathVariable int id) {
        return userService.getTokenById(id)
                .map(encryptedApiToken -> encryptedApiToken.getEncryptedApiToken())
                .map(encryptedApiToken -> new UserApiTokenDto(encryptedApiToken))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public Page<UserDto> findAll(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return userService.getAll(pageable)
                .map(user -> userMapper.fromUser(user));
    }

    @PostMapping
    @ApiOperation(value = "Add new User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 422, message = "Validation error")
    })
    public UserDto create(
            @ApiParam(value = "User object store in database table", required = true)
            @Valid @RequestBody UserDto userDto
    ) {
        var user = userMapper
                .toUser(userDto);
        return userMapper.fromUser(
                userService.createUser(user)
        );
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/{id}")
    public UserDto updateLocked(
            @Valid @RequestBody UserLockedDto userLockedDto,
            @PathVariable int id
    ) {
        return userService.getUserById(id)
                .map(user -> user.setLocked(userLockedDto.isLocked()))
                .map(user -> userService.save(user))
                .map(user -> userMapper.fromUser(user))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

}
