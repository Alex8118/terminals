package API;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.dariedu.terminals.monitoring.api.users.*;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

public class GetCurrentUserTests {

    @Test
    public void contextLoads() {
        UserService userService = Mockito.mock(UserService.class);
        UserMapper userMapper = Mockito.mock(UserMapper.class);
        CurrentUserService currentUserService = Mockito.mock(CurrentUserService.class);

        Mockito.when(userService.getUserById(any())).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.fromUser(any())).thenReturn(new UserDto());

        UserController userController = new UserController(userService, userMapper, currentUserService);

        userController.getCurrent();

        
    }
}
