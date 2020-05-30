package ru.dariedu.terminals.monitoring.api.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Integer id;

    @NotEmpty
    @Size(min = 2, max = 250, message = "Name must be between 2 and 250 characters")
    private String name;

    @NotEmpty
    @Email(message = "Email does not correct")
    private String email;

    @NotEmpty
    @JsonProperty(access = Access.WRITE_ONLY)
    @Pattern(regexp = "(?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16}", message = "Password must contain numbers, letters and symbols")
    private String password;

    private Boolean locked;

    private String role;

}
