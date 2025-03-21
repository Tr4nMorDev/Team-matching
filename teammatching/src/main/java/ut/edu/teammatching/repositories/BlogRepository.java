package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ut.edu.teammatching.models.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Lấy tất cả posts cùng với thông tin author (dùng EntityGraph để tránh N+1 problem)
    @EntityGraph(attributePaths = {"author"})
    List<Post> findAll();

    // Lấy post theo ID cùng với thông tin author
    @EntityGraph(attributePaths = {"author"})
    Optional<Post> findById(Long postId);

    // Lấy tất cả posts của một user
    List<Post> findByAuthor_Id(Long userId);

    // Đếm số lượng post của một user
    long countByAuthor_Id(Long userId);
}
