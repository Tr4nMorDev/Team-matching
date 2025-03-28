package ut.edu.teammatching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.models.Comment;
import ut.edu.teammatching.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Tạo bình luận mới
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.createComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    //Lấy ds bình luận theo postId
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostID(postId);
        return ResponseEntity.ok(comments);
    }
}