package ut.edu.teammatching.dto;

import ut.edu.teammatching.models.Blog;
import java.time.Instant;

public class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private String image;
    private String authorName;
    private Integer likeCount;
    private String authorAvatar;
    private Instant createdAt;  // Thêm trường createdAt

    public BlogDTO(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getContent();  // Nếu có trường tittle thì đổi lại cho hợp lý
        this.content = blog.getContent();
        this.authorName = blog.getAuthor().getFullName();
        this.likeCount = blog.getLikeCount();
        this.image = blog.getImages();
        this.authorAvatar = blog.getAuthor().getProfilePicture();
        this.createdAt = blog.getCreatedAt();  // Thêm gán createdAt
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
