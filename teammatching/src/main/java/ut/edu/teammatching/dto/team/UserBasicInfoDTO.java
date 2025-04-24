package ut.edu.teammatching.dto.team;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ut.edu.teammatching.enums.Role;

@Getter
@Setter
@Data
public class UserBasicInfoDTO {
    private Long id;
    private String profilePicture;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role; //STUDENT , LECTURER

    public UserBasicInfoDTO(Long id, String profilePicture, String fullName, String email, String phoneNumber, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.profilePicture = profilePicture;
    }
}