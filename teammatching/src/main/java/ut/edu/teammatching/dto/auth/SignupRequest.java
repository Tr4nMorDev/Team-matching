package ut.edu.teammatching.dto.auth;

import lombok.Getter;
import lombok.Setter;
import ut.edu.teammatching.enums.Gender;
import ut.edu.teammatching.enums.Role;

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private Gender gender;
    private Role role;
}
