package ut.edu.teammatching.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.teammatching.enums.TeamType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long id;
    private String teamName;
    private TeamType teamType;
    private String teamPicture;
    private String description;
    private String leaderName;
    private String lecturerName;
    private int membersCount;
}