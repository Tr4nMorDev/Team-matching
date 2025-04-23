package ut.edu.teammatching.dto.team;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserBasicInfoDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;

    public UserBasicInfoDTO(Long id, String fullName, String email, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}