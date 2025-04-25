package ut.edu.teammatching.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.dto.CommentRequest;
import ut.edu.teammatching.models.Blog;
import ut.edu.teammatching.models.Comment;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.repositories.BlogRepository;
import ut.edu.teammatching.repositories.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;

    public List<Comment> getCommentsByBlogId(Long blog) {
        return commentRepository.findByBlogId(blog);
    }

    public Comment createComment(CommentRequest request) {
        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }

        // Lấy proxy thay vì truy vấn toàn bộ dữ liệu
        Blog blogRef = entityManager.getReference(Blog.class, request.getBlogId());
        User userRef = entityManager.getReference(User.class, request.getUserId());

        // Kiểm tra sự tồn tại của Blog và User
        if (blogRef == null) {
            throw new IllegalArgumentException("Blog not found");
        }
        if (userRef == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Tạo và lưu comment
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setBlog(blogRef);
        comment.setAuthor(userRef);

        return commentRepository.save(comment);
    }

}
