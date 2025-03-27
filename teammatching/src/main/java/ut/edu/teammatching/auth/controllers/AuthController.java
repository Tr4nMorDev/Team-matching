package ut.edu.teammatching.auth.controllers;


import ut.edu.teammatching.dto.auth.LoginRequest;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.repositories.UserRepository;
import ut.edu.teammatching.auth.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public String signin(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty()) {
            return "User not found!";
        }

        User user = userOpt.get();
        System.out.println("Entered password: " + request.getPassword());
        System.out.println("Stored password: " + user.getPassword());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password!";
        }

        // ✅ Tạo JWT token
        return jwtUtil.generateToken(user.getUsername());
    }
}