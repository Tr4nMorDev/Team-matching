package ut.edu.teammatching.services;

import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.repositories.TeamRepository;
import ut.edu.teammatching.repositories.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final StudentRepository studentRepository;

    // Inject các repository vào TeamService
    public TeamService(TeamRepository teamRepository, StudentRepository studentRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    @Transactional
    public Team createTeam(Team team) {
        if (team.getLeader() == null || !team.getStudents().contains(team.getLeader())) {
            throw new IllegalStateException("Mỗi team phải có một leader hợp lệ!");
        }
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team teamDetails) {
        return teamRepository.findById(id).map(team -> {
            team.setTeamName(teamDetails.getTeamName());
            team.setTeamType(teamDetails.getTeamType());
            team.setDescription(teamDetails.getDescription());
            team.setTeamPicture(teamDetails.getTeamPicture());
            return teamRepository.save(team);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));
    }
    @Transactional
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy team để xóa!");
        }
        teamRepository.deleteById(id);
    }

    /**
     * Thiết lập leader mới cho team.
     */
    @Transactional
    public Team setLeader(Long teamId, Long studentId) {
        // Tìm team theo ID
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));

        // Tìm student theo ID
        Student newLeader = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên!"));

        // Kiểm tra leader mới có thuộc team không
        if (!team.getStudents().contains(newLeader)) {
            throw new IllegalStateException("Leader phải là thành viên của team!");
        }

        // Đặt leader mới và lưu vào database
        team.setLeader(newLeader);
        return teamRepository.save(team);
    }

    /**
     * Xóa một sinh viên khỏi team.
     */
    @Transactional
    public void removeStudent(Long teamId, Long studentId) {
        // Tìm team theo ID
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));

        // Tìm student theo ID
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên!"));

        // Không cho phép xóa leader khỏi team
        if (student.equals(team.getLeader())) {
            throw new IllegalStateException("Không thể xóa leader khỏi team! Hãy chuyển leader trước.");
        }

        // Xóa sinh viên khỏi danh sách thành viên team
        team.getStudents().remove(student);
        teamRepository.save(team);
    }

//    /**
//     * Gửi thông báo đến tất cả thành viên trong team.
//     */
//    private void notifyAllMembers(Long teamId, String message) {
//        Team team = getTeamById(teamId);
//        team.getStudents().forEach(member ->
//                notificationService.sendNotification(member, message)
//        );
//
//        if (team.getLecturer() != null) {
//            notificationService.sendNotification(team.getLecturer(), message);
//        }
//    }
}
