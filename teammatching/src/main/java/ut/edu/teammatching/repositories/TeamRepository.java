package ut.edu.teammatching.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.models.Student;
import ut.edu.teammatching.models.Lecturer;

import java.util.Optional;
import java.util.List;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @NonNull
    Optional<Team> findById(@NonNull Long id);


    // Tìm tất cả team mà student này là thành viên
    List<Team> findAllByStudentsContains(Student student);

    // Tìm tất cả team có leader là student
    List<Team> findAllByLeader(Student leader);

    // Tìm tất cả team do lecturer hướng dẫn
    List<Team> findAllByLecturer(Lecturer lecturer);

    @Query("SELECT t FROM Team t WHERE " +
            "LOWER(t.teamName) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    List<Team> searchTeam(String keyword);
}
