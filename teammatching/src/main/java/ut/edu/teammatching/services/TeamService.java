package ut.edu.teammatching.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.enums.TeamType;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.repositories.TeamRepository;
import ut.edu.teammatching.repositories.StudentRepository;
import ut.edu.teammatching.repositories.LecturerRepository;
import org.springframework.security.core.Authentication;
import ut.edu.teammatching.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;
    private final UserRepository userRepository;

    // Constructor sẽ được Spring tự động gọi khi tạo instance của TeamService
    public TeamService(TeamRepository teamRepository, StudentRepository studentRepository,
                       LecturerRepository lecturerRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
        this.userRepository = userRepository; // Inject UserRepository vào constructor
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    @Transactional
    public Team createTeam(String teamName, String description, TeamType teamType, String teamPicture, Authentication authentication) {
        // Lấy thông tin người dùng từ JWT token
        String username = authentication.getName();  // Lấy username từ JWT token
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy người dùng!");
        }

        User creator = userOpt.get();

        // Tạo team mới
        Team team = new Team();
        team.setTeamName(teamName);
        team.setDescription(description);
        team.setTeamType(teamType);
        team.setTeamPicture(teamPicture);
        team.setCreatedBy(creator);  // luôn nên gán createdBy

        // Kiểm tra xem creator là giảng viên hay sinh viên và xử lý tương ứng
        if (creator instanceof Lecturer) {
            Lecturer lecturer = (Lecturer) creator;
            team.setLecturer(lecturer);
        } else if (creator instanceof Student) {
            Student student = (Student) creator;
            team.getStudents().add(student);   // Thêm vào team trước
            team.setLeader(student);           // Rồi mới set leader
        }

        // Lưu team vào cơ sở dữ liệu
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

    @Transactional
    public void addStudent(Long teamId, Long studentIdToAdd, Long currentStudentId) {
        // Lấy team theo ID
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));

        // Lấy sinh viên hiện tại
        Student currentStudent = studentRepository.findById(currentStudentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên!"));

        // Kiểm tra xem sinh viên hiện tại có phải là thành viên của team không
        if (!team.getStudents().contains(currentStudent)) {
            throw new IllegalStateException("Chỉ thành viên trong team mới có quyền thêm người!");
        }

        // Lấy sinh viên cần thêm vào team
        Student studentToAdd = studentRepository.findById(studentIdToAdd)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên cần thêm!"));

        // Nếu sinh viên này chưa có trong team, thêm vào
        if (!team.getStudents().contains(studentToAdd)) {
            team.getStudents().add(studentToAdd);
            // Nếu leader chưa được chỉ định, gán leader là sinh viên đầu tiên trong team
            if (team.getLeader() == null && !team.getStudents().isEmpty()) {
                team.setLeader(team.getStudents().get(0)); // Gán leader là sinh viên đầu tiên
            }
            teamRepository.save(team);
        } else {
            throw new IllegalStateException("Sinh viên này đã là thành viên của team!");
        }
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
