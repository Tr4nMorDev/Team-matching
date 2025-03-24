package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.teammatching.models.Blog;
import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    // Lấy tất cả posts cùng với thông tin author (dùng EntityGraph để tránh N+1 problem)
    @EntityGraph(attributePaths = {"author"})
    List<Blog> findAll();

    // Lấy post theo ID cùng với thông tin author
    @EntityGraph(attributePaths = {"author"})
    Optional<Blog> findById(Long postId);

    // Lấy tất cả posts của một user
    List<Blog> findByAuthor_Id(Long userId);

    // Đếm số lượng post của một user
    long countByAuthor_Id(Long userId);
}
