package ut.edu.teammatching.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.dto.auth.SignupRequest;
import ut.edu.teammatching.enums.Role;
import ut.edu.teammatching.enums.Gender;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Tạo user dựa vào role
        User user;
        if (request.getRole() == Role.STUDENT) {
            user = new Student(
                    request.getUsername(),
                    request.getFullName(),
                    request.getEmail(),
                    encodedPassword,
                    Role.STUDENT,
                    request.getGender(),
                    request.getProfilePicture(),  // Sửa ở đây
                    request.getSkills(),          // Sửa ở đây
                    request.getHobbies(),         // Sửa ở đây
                    request.getProjects(),        // Sửa ở đây
                    request.getPhoneNumber(),     // Sửa ở đây
                    request.getMajor(),           // Sửa ở đây
                    request.getTerm()             // Sửa ở đây
            );
        } else if (request.getRole() == Role.LECTURER) {
            user = new Lecturer(
                    request.getUsername(),
                    request.getFullName(),
                    request.getEmail(),
                    encodedPassword,
                    Role.LECTURER,
                    request.getGender(),
                    request.getProfilePicture(),  // Sửa ở đây
                    request.getSkills(),          // Sửa ở đây
                    request.getHobbies(),         // Sửa ở đây
                    request.getProjects(),        // Sửa ở đây
                    request.getPhoneNumber(),     // Sửa ở đây
                    request.getDepartment(),      // Sửa ở đây
                    request.getResearchAreas()    // Sửa ở đây
            );
        }
        else {
            throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }

        userRepository.save(user);
    }
}
