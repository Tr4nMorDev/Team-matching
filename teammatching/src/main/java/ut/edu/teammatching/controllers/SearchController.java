package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ut.edu.teammatching.dto.team.TeamDTO;
import ut.edu.teammatching.dto.UserDTO;
import ut.edu.teammatching.dto.team.UserBasicInfoDTO;
import ut.edu.teammatching.services.LecturerService;
import ut.edu.teammatching.services.StudentService;
import ut.edu.teammatching.services.TeamService;
import ut.edu.teammatching.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final UserService userService;
    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam("keyword") String keyword,
            @RequestParam("currentUserId") Long currentUserId // 👈 nhận ID của người tìm kiếm
    ) {
        List<UserDTO> users = userService.searchUsers(keyword, currentUserId);
        List<TeamDTO> teams = teamService.searchTeams(keyword);

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("teams", teams);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/student")
    public ResponseEntity<List<UserBasicInfoDTO>> searchStudents(
            @RequestParam("keyword") String keyword,
            @RequestParam("currentUserId") Long currentUserId
    ) {
        List<UserBasicInfoDTO> results = studentService.searchStudentsByKeyword(keyword, currentUserId);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/lecturer")
    public ResponseEntity<List<UserBasicInfoDTO>> searchLecturers(
            @RequestParam("keyword") String keyword,
            @RequestParam("currentUserId") Long currentUserId
    ) {
        List<UserBasicInfoDTO> results = lecturerService.searchLecturersByKeyword(keyword, currentUserId);
        return ResponseEntity.ok(results);
    }
}
