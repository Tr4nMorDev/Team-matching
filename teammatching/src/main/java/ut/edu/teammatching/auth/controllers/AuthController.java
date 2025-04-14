package ut.edu.teammatching.auth.controllers;


import ut.edu.teammatching.dto.UserDTO;
import ut.edu.teammatching.dto.auth.LoginRequest;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.repositories.UserRepository;
import ut.edu.teammatching.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Map;
import ut.edu.teammatching.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found!"));
        }

        User user = userOpt.get();
        System.out.println("Entered password: " + request.getPassword());
        System.out.println("Stored password: " + user.getPassword());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid password!"));
        }

        // ✅ Tạo JWT token
        String token = jwtUtil.generateToken(user.getUsername());
        UserDTO userDTO = UserService.convertToDTO(user);
        // ✅ Trả về JSON chứa token + user info
        return ResponseEntity.ok(Map.of(
                "token", token,
                "userData", userDTO
        ));
    }

}