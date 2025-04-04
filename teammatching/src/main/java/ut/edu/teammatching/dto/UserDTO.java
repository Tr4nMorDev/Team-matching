package ut.edu.teammatching.dto;

import lombok.Data;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.User;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String role; // Dùng String để nhận "STUDENT" hoặc "LECTURER"
    private String gender;
    private String profilePicture;
    private String phoneNumber;

    private UserDetailDTO userID;


    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole().name();

        if (user instanceof Student student) {
            this.userID = new UserDetailDTO(student);
        } else if (user instanceof Lecturer lecturer) {
            this.userID = new UserDetailDTO(lecturer);
        }
    }
} 