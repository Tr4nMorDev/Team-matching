package ut.edu.teammatching.services;

import org.springframework.stereotype.Service;
import ut.edu.teammatching.dto.team.UserBasicInfoDTO;
import ut.edu.teammatching.enums.Role;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.repositories.LecturerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LecturerService {
    private LecturerRepository lecturerRepository;
    public List<UserBasicInfoDTO> searchLecturersByKeyword(String keyword, Long currentUserId) {
        List<Lecturer> matchedLecturers = lecturerRepository.findLecturersByKeyword(keyword, currentUserId);

        return matchedLecturers.stream()
                .map(lecturer -> new UserBasicInfoDTO(
                        lecturer.getId(),
                        lecturer.getProfilePicture(),
                        lecturer.getFullName(),
                        lecturer.getEmail(),
                        lecturer.getPhoneNumber(),
                        Role.LECTURER // 👈 Role LECTURER cho giảng viên
                ))
                .collect(Collectors.toList());
    }

}
