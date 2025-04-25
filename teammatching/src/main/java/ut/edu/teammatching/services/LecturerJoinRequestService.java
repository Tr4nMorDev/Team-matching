package ut.edu.teammatching.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.dto.team.LecturerJoinRequestDTO;
import ut.edu.teammatching.enums.JoinRequestStatus;
import ut.edu.teammatching.enums.TeamType;
import ut.edu.teammatching.exceptions.UserNotFoundException;
import ut.edu.teammatching.models.*;
import ut.edu.teammatching.repositories.LecturerJoinRequestRepository;
import ut.edu.teammatching.repositories.LecturerRepository;
import ut.edu.teammatching.repositories.NotificationRepository;
import ut.edu.teammatching.repositories.TeamRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LecturerJoinRequestService {
    private final LecturerRepository lecturerRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final LecturerJoinRequestRepository lecturerJoinRequestRepository;
    private final NotificationRepository notificationRepository;

    // Phương thức để lấy tất cả yêu cầu gia nhập có status PENDING cho một giảng viên
    @Transactional
    public List<LecturerJoinRequestDTO> getPendingRequestsForLecturer(Long lecturerId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new UserNotFoundException("Lecturer not found"));

        List<LecturerJoinRequest> pendingRequests =
                lecturerJoinRequestRepository.findByLecturerAndStatus(lecturer, JoinRequestStatus.PENDING);

        return pendingRequests.stream()
                .map(LecturerJoinRequestDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public LecturerJoinRequest sendLecturerJoinRequest(Long teamId, Long lecturerId, Long requesterId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy team!"));

        if (!team.getLeader().getId().equals(requesterId)) {
            throw new RuntimeException("Chỉ leader mới được gửi yêu cầu!");
        }

        if (team.getTeamType() != TeamType.ACADEMIC) {
            throw new RuntimeException("Chỉ áp dụng cho team loại ACADEMIC!");
        }

        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên!"));

        Optional<LecturerJoinRequest> existingRequest = lecturerJoinRequestRepository.findByTeamIdAndLecturerId(teamId, lecturerId);
        if (existingRequest.isPresent()) {
            throw new RuntimeException("Đã gửi yêu cầu trước đó!");
        }

        LecturerJoinRequest request = new LecturerJoinRequest();
        request.setTeam(team);
        request.setLecturer(lecturer);
        request.setStatus(JoinRequestStatus.PENDING);

        return lecturerJoinRequestRepository.save(request);
    }

    @Transactional
    public String respondToLecturerJoinRequest(Long requestId, Long lecturerId, Long leaderId, boolean accept) {
        LecturerJoinRequest request = lecturerJoinRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy yêu cầu!"));

        if (!request.getLecturer().getId().equals(lecturerId)) {
            throw new RuntimeException("Bạn không có quyền phản hồi yêu cầu này!");
        }

        Team team = request.getTeam();

        // Gán lại leader nếu cần (dựa vào leaderId truyền vào)
        if (leaderId != null) {
            if (team.getStudents().stream().noneMatch(s -> s.getId().equals(leaderId))) {
                throw new RuntimeException("Leader được chọn không phải là thành viên trong team!");
            }

            if (team.getLeader() == null || !team.getLeader().getId().equals(leaderId)) {
                team.setLeader(team.getStudents().stream()
                        .filter(s -> s.getId().equals(leaderId))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy leader trong team!")));
            }
        }

        // Kiểm tra sau khi đã gán lại leader
        if (team.getLeader() != null &&
                team.getStudents().stream().noneMatch(s -> s.getId().equals(team.getLeader().getId()))) {
            throw new IllegalStateException("Leader phải là thành viên của team!//");
        }

        if (request.getStatus() != JoinRequestStatus.PENDING) {
            throw new RuntimeException("Yêu cầu này đã được xử lý.");
        }

        if (accept) {
            request.setStatus(JoinRequestStatus.ACCEPTED);

            // Gọi teamService.addMember để thêm giảng viên vào team
            teamService.addMember(team.getId(), lecturerId, leaderId);  // Gọi teamService để thêm giảng viên vào team
        } else {
            request.setStatus(JoinRequestStatus.REJECTED);
        }

        lecturerJoinRequestRepository.save(request);

        return accept ? "Đã chấp nhận yêu cầu." : "Đã từ chối yêu cầu.";
    }

}
