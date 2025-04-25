package ut.edu.teammatching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.models.Comment;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.services.CommentService;
import ut.edu.teammatching.dto.CommentRequest;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // GET: Lấy danh sách comment theo blog
    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<Comment>> getCommentsByBlogId(@PathVariable Long blogId) {
        List<Comment> comments = commentService.getCommentsByBlogId(blogId);
        return ResponseEntity.ok(comments);
    }

    // POST: Thêm comment mới
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request,
                                            @AuthenticationPrincipal User user) {
        System.out.println("Đã vào add comment");
        System.out.println("User: " + user); // Xem giá trị của user
        System.out.println("📝 Nhận comment request:");
        System.out.println("postId: " + request.getBlogId());
        System.out.println("comment: " + request.getContent());
//        System.out.println("commentbyid: " + user.getId());

        if (user == null) {
            return ResponseEntity.status(401).body("Bạn chưa đăng nhập");
        }

        // 🖨️ Log giá trị nhận được từ frontend

        try {
            Comment comment = commentService.createComment(request, user);
            return ResponseEntity.ok(comment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
