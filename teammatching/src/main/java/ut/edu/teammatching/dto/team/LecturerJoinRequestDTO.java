package ut.edu.teammatching.dto.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ut.edu.teammatching.enums.JoinRequestStatus;
import ut.edu.teammatching.models.LecturerJoinRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LecturerJoinRequestDTO {
    private Long id;
    private Long teamId;
    private String teamName;
    private String teamPicture;
    private Long leaderId;
    private String leaderName;
    private Long lecturerId;
    private String lecturerName;
    private JoinRequestStatus status;

    public static LecturerJoinRequestDTO fromEntity(LecturerJoinRequest request) {
        return new LecturerJoinRequestDTO(
                request.getId(),
                request.getTeam().getId(),
                request.getTeam().getTeamName(),
                request.getTeam().getTeamPicture(),
                request.getTeam().getLeader().getId(),
                request.getTeam().getLeader().getFullName(),
                request.getLecturer().getId(),
                request.getLecturer().getFullName(),
                request.getStatus()
        );
    }
}
