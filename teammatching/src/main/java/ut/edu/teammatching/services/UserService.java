package ut.edu.teammatching.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.repositories.UserRepository;
import ut.edu.teammatching.models.User;
import java.util.List;
import ut.edu.teammatching.enums.Role;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Lecturer;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        // Kiểm tra dữ liệu cần thiết
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            throw new RuntimeException("Full Name is required");
        }
        if (user.getRole() != Role.STUDENT && user.getRole() != Role.LECTURER) {
            throw new RuntimeException("Role không hợp lệ: " + user.getRole());
        }
        // Kiểm tra role
        if (user.getRole() == null) {
            throw new RuntimeException("Role chưua thể lấy ");
        }

        User newUser;
        if (user.getRole() == Role.STUDENT) {
            if (!(user instanceof Student student)) {
                throw new RuntimeException("Invalid user data for student");
            }
            if (student.getMajor() == null || student.getMajor().isEmpty()) {
                throw new RuntimeException("Major is required for students");
            }
            if (student.getTerm() == null) {
                student.setTerm(1); // Mặc định là 1 nếu không có giá trị
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
