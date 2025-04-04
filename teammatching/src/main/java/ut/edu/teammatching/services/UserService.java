package ut.edu.teammatching.services;

//import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.repositories.UserRepository;
import ut.edu.teammatching.models.User;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Lấy danh sách tất cả users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy thông tin user theo ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
    }

    // Lấy thông tin user theo username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
    }

    // Lấy thông tin user theo email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
    }

    // Kiểm tra xem username có tồn tại không
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Kiểm tra xem email có tồn tại không
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Tạo mới user
    public User createUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Cập nhật thông tin user
    public User updateUser(Long id, User newUserData) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(newUserData.getUsername());
            user.setEmail(newUserData.getEmail());
            user.setSkills(newUserData.getSkills());
            user.setHobbies(newUserData.getHobbies());

            // Mã hóa password nếu có thay đổi
            if (newUserData.getPassword() != null && !newUserData.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(newUserData.getPassword()));
            }

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User không tồn tại"));
    }

    // Xóa user theo ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User không tồn tại");
        }
        userRepository.deleteById(id);
    }
}
