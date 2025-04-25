package ut.edu.teammatching.dto.team;

import lombok.Data;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.models.Team; // Thêm import Team

@Data
public class TeamMemberDTO {
    private Long id;
    private String fullName;
    private String memberPicture;
    private String type;  // "LEADER", "LECTURER", "MEMBER"

    public TeamMemberDTO(User user, Team team) { // Thêm tham số team
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.memberPicture= user.getProfilePicture();
        if (user instanceof Student) {
            System.out.println("Team Leader: " + team.getLeader());
            if (team.getLeader() != null && team.getLeader().getId().equals(user.getId())) {
                this.type = "LEADER"; // Nếu người dùng là Leader
            } else {
                this.type = "MEMBER"; // Nếu không phải là Leader
            }
        } else if (user instanceof Lecturer) {
            this.type = "LECTURER";  // Nếu người dùng là Lecturer
        }
    }
}
