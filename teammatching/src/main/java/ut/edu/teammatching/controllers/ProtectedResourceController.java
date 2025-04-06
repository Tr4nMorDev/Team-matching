package ut.edu.teammatching.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.services.UserService;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Blog;
import ut.edu.teammatching.models.Comment;
import ut.edu.teammatching.models.Notification;
import ut.edu.teammatching.models.Message;
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
        
        // Lấy thông tin user đầy đủ
        User user = userService.findByUsername(username);
        
        // Tạo response map với cấu trúc rõ ràng
        Map<String, Object> response = new HashMap<>();
        
        // Thông tin cơ bản
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("fullName", user.getFullName());
        response.put("gender", user.getGender());
        response.put("profilePicture", user.getProfilePicture());
        response.put("role", user.getRole());
        response.put("skills", user.getSkills());
        response.put("hobbies", user.getHobbies());
        response.put("projects", user.getProjects());
        response.put("phoneNumber", user.getPhoneNumber());
        
        // Thông tin Student hoặc Lecturer
        if (user instanceof Student) {
            Student student = (Student) user;
            response.put("major", student.getMajor());
            response.put("term", student.getTerm());
            response.put("teams", student.getTeams());
            response.put("assignedTasks", student.getAssignedTasks());
            response.put("receivedRatings", student.getReceivedRatings());
            response.put("givenRatings", student.getGivenRatings());
            response.put("leaders", student.getLeaders());
        } else if (user instanceof Lecturer) {
            Lecturer lecturer = (Lecturer) user;
            response.put("researchAreas", lecturer.getResearchAreas());
            response.put("receivedRatings", lecturer.getReceivedRatings());
            response.put("givenRatings", lecturer.getGivenRatings());
        }
        
        // Thông tin quan hệ
        response.put("blogs", user.getBlogs());
        response.put("comments", user.getComments());
        response.put("receivedNotifications", user.getReceivedNotifications());
        response.put("sentNotifications", user.getSentNotifications());
        response.put("sentMessages", user.getSentMessages());
        response.put("receivedMessages", user.getReceivedMessages());
        
        return ResponseEntity.ok(response);
    }
} 