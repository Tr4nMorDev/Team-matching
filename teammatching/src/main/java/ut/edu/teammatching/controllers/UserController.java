package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Lấy danh sách tất cả users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Lấy thông tin user theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Lấy thông tin user theo username
    @GetMapping("/search")
    public ResponseEntity<?> getUserByUsernameOrEmail(@RequestParam(required = false) String username,
                                                      @RequestParam(required = false) String email) {
        try {
            if (username != null) {
                return ResponseEntity.ok(userService.getUserByUsername(username));
            } else if (email != null) {
                return ResponseEntity.ok(userService.getUserByEmail(email));
            } else {
                return ResponseEntity.badRequest().body("Vui lòng cung cấp username hoặc email");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Tạo mới user
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Cập nhật thông tin user
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Xóa user theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
