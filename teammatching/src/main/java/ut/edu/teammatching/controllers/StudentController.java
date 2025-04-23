package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.dto.team.UserBasicInfoDTO;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.repositories.StudentRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping("/search-student")
    public ResponseEntity<?> searchStudent(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {

        Optional<Student> studentOpt = Optional.empty();

        if (email != null && !email.isBlank()) {
            studentOpt = studentRepository.findByEmail(email);
        } else if (phoneNumber != null && !phoneNumber.isBlank()) {
            studentOpt = studentRepository.findByPhoneNumber(phoneNumber);
        }

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            UserBasicInfoDTO dto = new UserBasicInfoDTO(
                    student.getId(),
                    student.getFullName(),
                    student.getEmail(),
                    student.getPhoneNumber()
            );
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sinh viên.");
        }
    }
}
