package ut.edu.teammatching.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProtectedResourceController {

    @GetMapping("/protected-resource")
    public ResponseEntity<?> getProtectedResource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        return ResponseEntity.ok()
            .body(Map.of(
                "message", "This is a protected resource",
                "username", username,
                "authenticated", true
            ));
    }
} 