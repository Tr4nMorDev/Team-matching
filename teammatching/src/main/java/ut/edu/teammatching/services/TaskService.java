package ut.edu.teammatching.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.enums.TaskStatus;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Task;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.repositories.StudentRepository;
import ut.edu.teammatching.repositories.TaskRepository;
import ut.edu.teammatching.repositories.TeamRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;
    private final StudentRepository studentRepository;

    public TaskService(TaskRepository taskRepository, TeamRepository teamRepository, StudentRepository studentRepository) {
        this.taskRepository = taskRepository;
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Lấy thông tin một task dựa trên ID.
     */
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy task!"));
    }

    /**
     * Xóa một task khỏi hệ thống.
     */
    @Transactional
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Không tìm thấy task để xóa!");
        }
        taskRepository.deleteById(taskId);
    }

    /**
     * Chỉ leader của team mới có thể giao task cho thành viên.
     */
    @Transactional
    public Task assignTask(Long leaderId, Long teamId, String taskName, String description, Long studentId) {
        // Kiểm tra team có tồn tại không
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));

        // Kiểm tra leader có hợp lệ không
        if (!team.getLeader().getId().equals(leaderId)) {
            throw new IllegalStateException("Chỉ leader của team mới có thể giao task!");
        }

        // Kiểm tra sinh viên có trong team không
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên!"));

        if (!team.getStudents().contains(student)) {
            throw new IllegalStateException("Sinh viên không thuộc team này!");
        }

        // Tạo mới task
        Task task = new Task();
        task.setTaskName(taskName);
        task.setDescription(description);
        task.setTeam(team);
        task.setAssignedToStudent(student);
        task.setStatus(TaskStatus.IN_PROGRESS);

        return taskRepository.save(task);
    }
}
