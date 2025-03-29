package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.enums.TaskStatus;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Task;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.services.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    // API lấy danh sách tất cả Task
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // API lấy Task theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // API tạo Task mới
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    // API xóa Task theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    // API cập nhật trạng thái Task (To Do, In Progress, Done)
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam TaskStatus status) {
        taskService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    // API gán Task cho một sinh viên
    @PutMapping("/{id}/assign")
    public ResponseEntity<Void> assignTask(@PathVariable Long id, @RequestBody Student student) {
        taskService.assignTo(id, student);
        return ResponseEntity.ok().build();
    }
}
