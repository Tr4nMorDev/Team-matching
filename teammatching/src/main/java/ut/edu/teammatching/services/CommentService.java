package ut.edu.teammatching.services;

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

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;

    public List<Comment> getCommentsByBlogId(Long blog) {
        return commentRepository.findByBlogId(blog);
    }

    public Comment createComment(CommentRequest request, User user) {


        Blog blog = blogRepository.findById(request.getBlogId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy blog"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setBlog(blog);
        comment.setAuthor(user);

        return commentRepository.save(comment);
    }
}
