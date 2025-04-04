package ut.edu.teammatching.dto;

import lombok.Data;
import ut.edu.teammatching.models.Lecturer;

@Data
public class LecturerDetailDTO {
    private String department;
    private String researchAreas;

    public LecturerDetailDTO(Lecturer lecturer) {
        this.department = lecturer.getDepartment();
        this.researchAreas = lecturer.getResearchAreas();
    }
}
