package ut.edu.teammatching.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.repositories.TeamRepository;
import ut.edu.teammatching.repositories.StudentRepository;
import ut.edu.teammatching.repositories.LecturerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;

    // Inject các repository vào TeamService
    public TeamService(TeamRepository teamRepository, StudentRepository studentRepository, LecturerRepository lecturerRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
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

    // Xóa team (chỉ Leader có thể xóa)
    @Transactional
    public void deleteTeam(Long leaderId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        if (!team.getLeader().getId().equals(leaderId)) {
            throw new RuntimeException("Only the team leader can delete the team");
        }
        teamRepository.delete(team);
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

    //Rời team
    @Transactional
    public void leaveTeam(Long studentId, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên!"));

        if (team.getLeader().getId().equals(studentId)) {
            throw new IllegalStateException("Leader không thể rời khỏi nhóm. Hãy chuyển Leader trước!");
        }

        team.getStudents().remove(student);
        teamRepository.save(team);
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

    // Thêm thành viên vào team (chỉ Leader có thể thêm)
    @Transactional
    public Team addMember(Long leaderId, Long teamId, Long studentId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));
        if (!team.getLeader().getId().equals(leaderId)) {
            throw new RuntimeException("Chỉ có Leader mới được thêm thành viên");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên!"));

        team.getStudents().add(student);
        return teamRepository.save(team);
    }

    // Bổ nhiệm giảng viên hướng dẫn
    @Transactional
    public Team assignLecturer(Long leaderId, Long teamId, Long lecturerId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));

        if (!team.getLeader().getId().equals(leaderId)) {
            throw new RuntimeException("Chỉ có Leader mới được bổ nhiệm giảng viên");
        }

        Lecturer lecturer = lecturerRepository.findById(lecturerId) // Đúng cách
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));

        team.setLecturer(lecturer);
        return teamRepository.save(team);
    }

    //Phân công vai trò trong team
    @Transactional
    public Team assignRole(Long leaderId, Long teamId, Long memberId, String role) {
        // Tìm team
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));

        // Kiểm tra người gọi có phải là leader không
        if (!team.getLeader().getId().equals(leaderId)) {
            throw new RuntimeException("Chỉ có Leader mới được phân công vai trò");
        }

        // Kiểm tra xem member có trong team không
        Student member = studentRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên!"));
        if (!team.getStudents().contains(member)) {
            throw new RuntimeException("Thành viên không có trong team");
        }

        // Gán vai trò
        team.getRoles().put(memberId, role);
        return teamRepository.save(team);
    }
}
