package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.enums.JoinRequestStatus;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.LecturerJoinRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerJoinRequestRepository extends JpaRepository<LecturerJoinRequest, Long> {
    Optional<LecturerJoinRequest> findByTeamIdAndLecturerId(Long teamId, Long lecturerId);
    List<LecturerJoinRequest> findByLecturerAndStatus(Lecturer lecturer, JoinRequestStatus status);
}
