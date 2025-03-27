package ut.edu.teammatching.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.repositories.UserRepository;
import ut.edu.teammatching.models.User;
import java.util.List;
import ut.edu.teammatching.enums.Role;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Lecturer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    //lay dnah sach tat ca users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //lay thong tin user theo id
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //lay thong tin user theo username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Tìm kiếm user theo từ khóa
    public List<User> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword);
    }

    //tao moi user
    public User createUser(User user) {

        logger.info("📌 Received request to create user: {}", user);

        User newUser;
        if (user.getRole() == Role.STUDENT) {
            if (!(user instanceof Student student)) {
                throw new RuntimeException("Invalid user data for student");
            }
            if (student.getMajor() == null || student.getMajor().isEmpty()) {
                throw new RuntimeException("Major is required for students");
            }

            newUser = new Student(
                    student.getUsername(), student.getFullName(), student.getEmail(), student.getPassword(),
                    student.getRole(), student.getGender(), student.getProfilePicture(),
                    student.getSkills(), student.getHobbies(), student.getProjects(), student.getPhoneNumber(),
                    student.getMajor(), student.getTerm()
            );
        } else if (user.getRole() == Role.LECTURER) {
            Lecturer lecturer = (Lecturer) user; // Ép kiểu User thành Lecturer
            newUser = new Lecturer(lecturer.getUsername(), lecturer.getFullName(), lecturer.getEmail(), lecturer.getPassword(),
                    lecturer.getRole(), lecturer.getGender(), lecturer.getProfilePicture(),
                    lecturer.getSkills(), lecturer.getHobbies(), lecturer.getProjects(), lecturer.getPhoneNumber(),
                    lecturer.getDepartment(), lecturer.getResearchAreas());
        } else {
            throw new IllegalArgumentException("Invalid role: " + user.getRole());
        }

        return userRepository.save(user);
    }
    //cap nhat thong tin user
    public User updateUser(Long id, User newUserData) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(newUserData.getUsername());
            user.setEmail(newUserData.getEmail());
//            user.setPassword(newUserData.getPassword());
            user.setSkills(newUserData.getSkills());
            user.setHobbies(newUserData.getHobbies());
            if (newUserData.getPassword() != null && !newUserData.getPassword().isEmpty()) {
                user.setPassword(newUserData.getPassword()); // Nên mã hóa password trước khi lưu
            }
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //xoa user theo id
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
