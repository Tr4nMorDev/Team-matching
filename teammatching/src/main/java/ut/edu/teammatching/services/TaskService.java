package ut.edu.teammatching.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ut.edu.teammatching.enums.TaskStatus;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Task;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.repositories.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    // Lấy danh sách tất cả các Task
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Lấy Task theo ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Lấy danh sách Task theo một Team cụ thể
    public List<Task> getTasksByTeam(Team team) {
        return taskRepository.findByTeam(team);
    }

    // Tạo một Task mới
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Xóa Task theo ID
    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Cập nhật trạng thái của Task (To Do, In Progress, Done)
    @Transactional
    public void updateStatus(Long taskId, TaskStatus newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(newStatus);
        taskRepository.save(task);
    }

    // Gán một Task cho một sinh viên cụ thể
    @Transactional
    public void assignTo(Long taskId, Student student) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setAssignedToStudent(student);
        taskRepository.save(task);
    }
}
