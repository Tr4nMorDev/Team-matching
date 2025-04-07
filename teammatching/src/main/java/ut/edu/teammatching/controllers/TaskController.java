package ut.edu.teammatching.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.models.Task;
import ut.edu.teammatching.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * API: Lấy thông tin một task dựa trên ID.
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    /**
     * API: Xóa một task.
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task đã được xóa thành công!");
    }

    /**
     * API: Chỉ leader của team mới có thể giao task cho thành viên.
     */
    @PostMapping("/assign")
    public ResponseEntity<Task> assignTask(
            @RequestParam Long leaderId,
            @RequestParam Long teamId,
            @RequestParam String taskName,
            @RequestParam String description,
            @RequestParam Long studentId) {
        Task task = taskService.assignTask(leaderId, teamId, taskName, description, studentId);
        return ResponseEntity.ok(task);
    }
}
