package ut.edu.teammatching.dto;

import lombok.Data;
import ut.edu.teammatching.enums.TeamType;

@Data
public class CreateTeamDTO {
    private String teamName;
    private String description;
    private TeamType teamType;  // ACADEMIC or EXTERNAL
    private String teamPicture;
    // Không cần thêm createdBy vì sẽ lấy từ Authentication
}
