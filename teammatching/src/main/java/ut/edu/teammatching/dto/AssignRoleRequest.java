package ut.edu.teammatching.dto;

import lombok.Data;

@Data
public class AssignRoleRequest {
    private Long leaderId;
    private Long teamId;
    private Long memberId;
    private String role;

    // Getters & Setters
}
