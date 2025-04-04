package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.dto.AssignRoleRequest;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.services.TeamService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamService.getTeamById(id);
        return team.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        return ResponseEntity.ok(teamService.createTeam(team));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return ResponseEntity.ok(teamService.updateTeam(id, team));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<String> deleteTeam(@RequestParam Long leaderId,
                                             @PathVariable Long teamId) {
        teamService.deleteTeam(leaderId, teamId);
        return ResponseEntity.ok("Team deleted successfully");
    }

    //Đặt một sinh viên làm leader của team.
    @PutMapping("/{id}/leader/{studentId}")
    public ResponseEntity<Team> setLeader(@PathVariable Long id, @PathVariable Long studentId) {
        return ResponseEntity.ok(teamService.setLeader(id, studentId));
    }

    //Xóa một sinh viên khỏi team.
    @DeleteMapping("/{id}/remove-student/{studentId}")
    public ResponseEntity<Void> removeStudent(@PathVariable Long id, @PathVariable Long studentId) {
        teamService.removeStudent(id, studentId);
        return ResponseEntity.ok().build();
    }

    //Thêm thành viên
    @PostMapping("/{teamId}/add-member")
    public ResponseEntity<Team> addMember(@RequestParam Long leaderId,
                                          @PathVariable Long teamId,
                                          @RequestParam Long studentId) {
        Team team = teamService.addMember(leaderId, teamId, studentId);
        return ResponseEntity.ok(team);
    }

    //Thêm giảng viên hướng dẫn
    @PostMapping("/{teamId}/assign-lecturer")
    public ResponseEntity<Team> assignLecturer(@RequestParam Long leaderId,
                                               @PathVariable Long teamId,
                                               @RequestParam Long lecturerId) {
        Team team = teamService.assignLecturer(leaderId, teamId, lecturerId);
        return ResponseEntity.ok(team);
    }

    //Phân công vai trò
    @PostMapping("/{teamId}/assign-role")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleRequest request) {
        Team updatedTeam = teamService.assignRole(request.getLeaderId(), request.getTeamId(), request.getMemberId(), request.getRole());
        return ResponseEntity.ok(updatedTeam);
    }
}
