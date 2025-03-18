package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ut.edu.teammatching.models.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Tìm user theo username
    @Query(value = "SELECT * FROM user WHERE userName = :userName", nativeQuery = true)
    Optional<User> findByUserName(@Param("userName") String userName);
    
    // Tìm user và load eager cả posts
    @Query(value = "SELECT DISTINCT u.* FROM user u " +
           "LEFT JOIN post p ON u.userId = p.authorId " +
           "WHERE u.userId = :userId", nativeQuery = true)
    Optional<User> findByIdWithDetails(@Param("userId") Long userId);
    
    // Lấy tất cả users với thông tin cơ bản
    @Query(value = "SELECT * FROM user", nativeQuery = true)
    List<User> findAllUsers();
    
    // Lấy tất cả users với đầy đủ thông tin
    @Query(value = "SELECT DISTINCT u.* FROM user u " +
           "LEFT JOIN post p ON u.userId = p.authorId", nativeQuery = true)
    List<User> findAllWithDetails();
    
    // Tìm users theo role
    @Query(value = "SELECT * FROM user WHERE role = :role", nativeQuery = true)
    List<User> findByRole(@Param("role") String role);
    
    // Tìm kiếm users theo keyword
    @Query(value = "SELECT * FROM user WHERE " +
           "LOWER(userName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<User> searchUsers(@Param("keyword") String keyword);
    
    // Kiểm tra username đã tồn tại chưa
    @Query(value = "SELECT COUNT(*) > 0 FROM user WHERE userName = :userName", nativeQuery = true)
    boolean existsByUserName(@Param("userName") String userName);
    
    // Đếm số lượng user theo role
    @Query(value = "SELECT COUNT(*) FROM user WHERE role = :role", nativeQuery = true)
    long countByRole(@Param("role") String role);
}
