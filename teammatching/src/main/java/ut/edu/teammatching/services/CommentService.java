package ut.edu.teammatching.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.models.Comment;
import ut.edu.teammatching.repositories.CommentRepository;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByBlogId(Long blog) {
        return commentRepository.findByBlogId(blog);
    }
}
