package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ut.edu.teammatching.models.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Lấy tất cả posts với thông tin author
    @Query(value = "SELECT p.*, u.* FROM post p " +
           "LEFT JOIN user u ON p.authorId = u.userId", nativeQuery = true)
    List<Post> findAllWithAuthor();

    // Lấy post theo ID với thông tin author
    @Query(value = "SELECT p.*, u.* FROM post p " +
           "LEFT JOIN user u ON p.authorId = u.userId " +
           "WHERE p.postId = :postId", nativeQuery = true)
    Post findByIdWithAuthor(@Param("postId") Long postId);

    // Lấy tất cả posts của một user
    @Query(value = "SELECT * FROM post WHERE authorId = :userId", nativeQuery = true)
    List<Post> findByAuthorId(@Param("userId") Long userId);

    // Đếm số lượng post của một user
    @Query(value = "SELECT COUNT(*) FROM post WHERE authorId = :userId", nativeQuery = true)
    long countByAuthorId(@Param("userId") Long userId);
}
