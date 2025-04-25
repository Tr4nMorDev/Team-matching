package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ut.edu.teammatching.models.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE " +
            "(LOWER(s.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR s.phoneNumber LIKE CONCAT('%', :keyword, '%')) " +
            "AND s.id <> :currentUserId")
    List<Student> findStudentsByKeyword(@Param("keyword") String keyword, @Param("currentUserId") Long currentUserId);
}
