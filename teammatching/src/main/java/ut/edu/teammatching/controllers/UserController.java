package ut.edu.teammatching.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.repositories.UserRepository;
import ut.edu.teammatching.models.User;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            logger.info("Fetching all users");
            List<User> users = userRepository.findAllUsers(); // Đổi sang findAllUsers() thay vì findAllWithDetails()
            logger.info("Found {} users", users.size());
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching all users", e);
            return ResponseEntity.internalServerError().body("Error fetching users: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            logger.info("Fetching user with id: {}", id);
            return userRepository.findById(id) // Đổi sang findById thay vì findByIdWithDetails
                    .map(user -> {
                        logger.info("Found user: {}", user.getUserName());
                        return ResponseEntity.ok(user);
                    })
                    .orElseGet(() -> {
                        logger.warn("User not found with id: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            logger.error("Error fetching user with id: " + id, e);
            return ResponseEntity.internalServerError().body("Error fetching user: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam String keyword) {
        try {
            logger.info("Searching users with keyword: {}", keyword);
            List<User> users = userRepository.searchUsers(keyword);
            logger.info("Found {} users matching keyword: {}", users.size(), keyword);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error searching users with keyword: " + keyword, e);
            return ResponseEntity.internalServerError().body("Error searching users: " + e.getMessage());
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<?> getUsersByRole(@PathVariable String role) {
        try {
            logger.info("Fetching users with role: {}", role);
            List<User> users = userRepository.findByRole(role);
            logger.info("Found {} users with role: {}", users.size(), role);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error fetching users with role: " + role, e);
            return ResponseEntity.internalServerError().body("Error fetching users by role: " + e.getMessage());
        }
    }

    @GetMapping("/username/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        try {
            logger.info("Fetching user with username: {}", userName);
            return userRepository.findByUserName(userName)
                    .map(user -> {
                        logger.info("Found user: {}", user.getUserName());
                        return ResponseEntity.ok(user);
                    })
                    .orElseGet(() -> {
                        logger.warn("User not found with username: {}", userName);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            logger.error("Error fetching user with username: " + userName, e);
            return ResponseEntity.internalServerError().body("Error fetching user: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            logger.info("Creating new user with username: {}", user.getUserName());
            if (userRepository.existsByUserName(user.getUserName())) {
                logger.warn("Username already exists: {}", user.getUserName());
                return ResponseEntity.badRequest().body("Username already exists");
            }
            
            User savedUser = userRepository.save(user);
            logger.info("Created user with id: {}", savedUser.getId());
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return ResponseEntity.internalServerError().body("Error creating user: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            logger.info("Updating user with id: {}", id);
            return userRepository.findById(id)
                    .map(user -> {
                        if (!user.getUserName().equals(updatedUser.getUserName()) && 
                            userRepository.existsByUserName(updatedUser.getUserName())) {
                            logger.warn("Username already exists: {}", updatedUser.getUserName());
                            return ResponseEntity.badRequest().body("Username already exists");
                        }
                        
                        user.setUserName(updatedUser.getUserName());
                        user.setPassword(updatedUser.getPassword());
                        user.setFullName(updatedUser.getFullName());
                        user.setEmail(updatedUser.getEmail());
                        user.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
                        user.setRole(updatedUser.getRole());
                        user.setGender(updatedUser.getGender());
                        user.setSkills(updatedUser.getSkills());
                        user.setHobby(updatedUser.getHobby());
                        user.setProjects(updatedUser.getProjects());
                        user.setPhoneNumber(updatedUser.getPhoneNumber());
                        
                        User savedUser = userRepository.save(user);
                        logger.info("Updated user with id: {}", savedUser.getId());
                        return ResponseEntity.ok(savedUser);
                    })
                    .orElseGet(() -> {
                        logger.warn("User not found with id: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            logger.error("Error updating user with id: " + id, e);
            return ResponseEntity.internalServerError().body("Error updating user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            logger.info("Deleting user with id: {}", id);
            return userRepository.findById(id)
                    .map(user -> {
                        userRepository.delete(user);
                        logger.info("Deleted user with id: {}", id);
                        return ResponseEntity.ok().build();
                    })
                    .orElseGet(() -> {
                        logger.warn("User not found with id: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (Exception e) {
            logger.error("Error deleting user with id: " + id, e);
            return ResponseEntity.internalServerError().body("Error deleting user: " + e.getMessage());
        }
    }

    @GetMapping("/count/{role}")
    public ResponseEntity<Long> countUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userRepository.countByRole(role));
    }
}
