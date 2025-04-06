package ut.edu.teammatching.dto;

import lombok.Data;
import ut.edu.teammatching.models.Student;

@Data
public class StudentDetailDTO {
    private String major;
    private int term;

    public StudentDetailDTO(Student student) {
        this.major = student.getMajor();
        this.term = student.getTerm();
    }
}
