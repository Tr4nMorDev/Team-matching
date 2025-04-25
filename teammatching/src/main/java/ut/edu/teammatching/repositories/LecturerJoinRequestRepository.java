package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.teammatching.models.LecturerJoinRequest;

import java.util.Optional;

public interface LecturerJoinRequestRepository extends JpaRepository<LecturerJoinRequest, Long> {
    Optional<LecturerJoinRequest> findByTeamIdAndLecturerId(Long teamId, Long lecturerId);
}
