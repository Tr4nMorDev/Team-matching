package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.dto.team.UserBasicInfoDTO;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.repositories.LecturerRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/lecturers")
@RequiredArgsConstructor
public class LecturerController {

    private final LecturerRepository lecturerRepository;

    @GetMapping("/search")
    public ResponseEntity<?> searchLecturer(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {

        Optional<Lecturer> lecturerOpt = Optional.empty();

        if (email != null && !email.isBlank()) {
            lecturerOpt = lecturerRepository.findByEmail(email);
        } else if (phoneNumber != null && !phoneNumber.isBlank()) {
            lecturerOpt = lecturerRepository.findByPhoneNumber(phoneNumber);
        }

        if (lecturerOpt.isPresent()) {
            Lecturer lecturer = lecturerOpt.get();
            UserBasicInfoDTO dto = new UserBasicInfoDTO(
                    lecturer.getId(),
                    lecturer.getFullName(),
                    lecturer.getEmail(),
                    lecturer.getPhoneNumber()
            );
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy giảng viên.");
        }
    }
}
