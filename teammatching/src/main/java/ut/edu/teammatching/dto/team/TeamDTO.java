package ut.edu.teammatching.dto.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.teammatching.enums.TeamType;
import ut.edu.teammatching.models.Team;

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

    public static TeamDTO fromTeam(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setTeamName(team.getTeamName());
        dto.setTeamType(team.getTeamType());
        dto.setTeamPicture(team.getTeamPicture());
        dto.setDescription(team.getDescription());

        // set thêm leader name
        if (team.getLeader() != null) {
            dto.setLeaderName(team.getLeader().getFullName());
        }

        // set thêm lecturer name
        if (team.getLecturer() != null) {
            dto.setLecturerName(team.getLecturer().getFullName());
        }

        // set members count
        dto.setMembersCount(team.getStudents().size());

        return dto;
    }
}