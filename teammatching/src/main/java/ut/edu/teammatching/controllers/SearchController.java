package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ut.edu.teammatching.dto.TeamDTO;
import ut.edu.teammatching.dto.UserDTO;
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
    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> search(@RequestParam("keyword") String keyword) {
        List<UserDTO> users = userService.searchUsers(keyword);
        List<TeamDTO> teams = teamService.searchTeams(keyword);

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("teams", teams);
        return ResponseEntity.ok(response);
    }
}
