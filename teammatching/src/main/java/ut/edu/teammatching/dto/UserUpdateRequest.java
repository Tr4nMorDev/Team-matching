package ut.edu.teammatching.dto;

import lombok.Data;
import ut.edu.teammatching.enums.Gender;

import java.util.List;

@Data
public class UserUpdateRequest {
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String role; // Dùng String để nhận "STUDENT" hoặc "LECTURER"
    private String gender;
    private String profilePicture;
    private List<String> skills;
    private List<String> hobbies;
    private List<String> projects;
    private String phoneNumber;
    private String major;
    private Integer term;
    private String department;
    private List<String> researchAreas;
} 