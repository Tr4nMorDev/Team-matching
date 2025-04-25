package ut.edu.teammatching.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.services.UserService;
import ut.edu.teammatching.dto.UserDTO;
import ut.edu.teammatching.dto.StudentDetailDTO;
import ut.edu.teammatching.dto.LecturerDetailDTO;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProtectedResourceController {
    private final UserService userService;

    public ProtectedResourceController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/protected-resource")
    public ResponseEntity<?> getProtectedResource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Lấy thông tin người dùng từ dịch vụ
        User user = userService.findByUsername(username);

        // Tạo DTO cho user
        UserDTO userDTO = new UserDTO(user);

        // Tạo response map
        Map<String, Object> response = new HashMap<>();
        response.put("user", userDTO);

        // Cung cấp thêm thông tin chi tiết tùy theo loại người dùng (Student or Lecturer)
        if (user instanceof Student student) {
            response.put("details", new StudentDetailDTO(student));  // Cung cấp thông tin chi tiết của Student
        } else if (user instanceof Lecturer lecturer) {
            response.put("details", new LecturerDetailDTO(lecturer));  // Cung cấp thông tin chi tiết của Lecturer
        }

        return ResponseEntity.ok(response);
    }
}
