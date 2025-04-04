package ut.edu.teammatching.dto;

import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Student;

import java.util.List;
import lombok.Data;

@Data
public class UserDetailDTO {
    private String major; // Nếu là Student
    private int term;
    private String department; // Nếu là Lecturer
    private String researchAreas;
    private List<String> skills;
    private List<String> hobbies;
    private List<String> projects;

    public UserDetailDTO(Student student) {
        this.major = student.getMajor();
        this.term = student.getTerm();
    }

    public UserDetailDTO(Lecturer lecturer) {
        this.department = lecturer.getDepartment();
        this.researchAreas = lecturer.getResearchAreas();
    }
}
