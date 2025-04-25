package ut.edu.teammatching.dto.auth;

import lombok.Getter;
import lombok.Setter;
import ut.edu.teammatching.enums.Gender;
import ut.edu.teammatching.enums.Role;

import java.util.List;

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private Gender gender;
    private Role role;

    // Shared fields
    private String profilePicture;
    private List<String> skills;
    private List<String> hobbies;
    private List<String> projects;
    private String phoneNumber;

    // Student-specific
    private String major;
    private int term;

    // Lecturer-specific
    private String department;
    private String researchAreas;
}

