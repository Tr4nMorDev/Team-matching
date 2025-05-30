package ut.edu.teammatching.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.Event;
import ut.edu.teammatching.dto.UserDTO;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {
        "blogs"
    })
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    boolean existsByUsername(String username);
    long countByRole(Role role);

    // Kiểm tra username đã tồn tại chưa
//    boolean existsByUsername(String username);

    // Tìm user và load danh sách bài blog (dùng EntityGraph để Eager Fetch)
    @EntityGraph(attributePaths = {"blogs"})
    @NonNull
    Optional<User> findById(@NonNull Long id);

    @Query("SELECT u FROM User u WHERE " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "u.id <> :currentUserId")
    List<User> searchUsers(@Param("keyword") String keyword, @Param("currentUserId") Long currentUserId);

}