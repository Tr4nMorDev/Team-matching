package ut.edu.teammatching.repositories;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.models.User;

import java.util.Optional;


public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    @NonNull
    Optional<Lecturer> findById(@NonNull Long id);
}
