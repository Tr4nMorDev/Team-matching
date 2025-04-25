package ut.edu.teammatching.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import ut.edu.teammatching.enums.JoinRequestStatus;

@Entity
@Table(name = "lecturer_join_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LecturerJoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Team gửi yêu cầu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonIgnoreProperties({"lecturer", "students", "roles", "joinRequests", "tasks"})
    private Team team;

    // Giảng viên nhận yêu cầu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    @JsonIgnoreProperties({"supervisedTeams", "sentMessages", "givenRatings", "receivedRatings"})
    private Lecturer lecturer;

    @Enumerated(EnumType.STRING)
    private JoinRequestStatus status = JoinRequestStatus.PENDING;
}
