package ut.edu.teammatching.services;

import org.springframework.stereotype.Service;
import ut.edu.teammatching.dto.team.UserBasicInfoDTO;
import ut.edu.teammatching.enums.Role;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<UserBasicInfoDTO> searchStudentsByKeyword(String keyword) {
        List<Student> matchedStudents = studentRepository.findStudentsByKeyword(keyword);

        return matchedStudents.stream()
                .map(student -> new UserBasicInfoDTO(
                        student.getId(),
                        student.getProfilePicture(),
                        student.getFullName(),
                        student.getEmail(),
                        student.getPhoneNumber(),
                        Role.STUDENT // 👈 chắc chắn là STUDENT rồi
                ))
                .collect(Collectors.toList());
    }
}
