package ut.edu.teammatching.dto;

import lombok.Data;
import ut.edu.teammatching.models.Blog;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class BlogDTO {
    private Long id; // 👈 Thêm dòng này
    private String title;
    private String content;
    private String image;
    private String authorName;
    private Integer likeCount;
    private String authorAvatar;  // Thêm trường authorAvatar
    private LocalDateTime createdAt;

    public BlogDTO(Blog blog) {
        this.id = blog.getId(); // 👈 Nhớ set luôn ID
        this.title = blog.getContent();  // Assuming you want to use a separate 'title' field, you may need to adjust this.
        this.content = blog.getContent();
        this.authorName = blog.getAuthor().getFullName();
        this.likeCount = blog.getLikeCount();
        this.image = blog.getImages();
        this.authorAvatar = blog.getAuthor().getProfilePicture();
        this.createdAt = blog.getCreatedAt();
    }
}
