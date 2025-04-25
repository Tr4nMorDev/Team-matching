package ut.edu.teammatching.dto;

import lombok.Data;
import ut.edu.teammatching.models.Blog;
import ut.edu.teammatching.models.Comment;
import java.time.Instant;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BlogDTO {
    // Getters and Setters
    private Long id;
    private String title;
    private String content;
    private String image;
    private String authorName;
    private Integer likeCount;
    private String authorAvatar;
    private Instant createdAt;  // Thêm trường createdAt
    private List<CommentDTO> comments; // Thêm trường để chứa danh sách comment


    public BlogDTO(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getContent();  // Nếu có trường tittle thì đổi lại cho hợp lý
        this.content = blog.getContent();
        this.authorName = blog.getAuthor().getFullName();
        this.likeCount = blog.getLikeCount();
        this.image = blog.getImages();
        this.authorAvatar = blog.getAuthor().getProfilePicture();

        this.createdAt = blog.getCreatedAt();  // Thêm gán createdAt
        this.comments = blog.getComments().stream()
                .map(comment -> new CommentDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getAuthor().getFullName(),
                        comment.getAuthor().getProfilePicture()
                ))
                .collect(Collectors.toList());
    }

}
