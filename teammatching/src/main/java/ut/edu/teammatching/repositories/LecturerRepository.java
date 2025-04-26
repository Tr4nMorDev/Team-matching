package ut.edu.teammatching.repositories;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ut.edu.teammatching.models.Lecturer;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
    @NonNull
    Optional<Lecturer> findById(@NonNull Long id);

    Optional<Lecturer> findByEmail(String email);
    Optional<Lecturer> findByPhoneNumber(String phoneNumber);
    @Query("SELECT l FROM Lecturer l WHERE (" +
            "(LOWER(l.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(l.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR l.phoneNumber LIKE CONCAT('%', :keyword, '%'))) ")
    List<Lecturer> findLecturersByKeyword(@Param("keyword") String keyword);

}
