package ut.edu.teammatching.dto;

import ut.edu.teammatching.models.Blog;

public class BlogDTO {
    private String title;
    private String content;
    private String image;
    private String authorName;
    private Integer likeCount;
    private String authorAvatar;  // Thêm trường authorAvatar

    public BlogDTO(Blog blog) {
        this.title = blog.getContent();  // Assuming you want to use a separate 'title' field, you may need to adjust this.
        this.content = blog.getContent();
        this.authorName = blog.getAuthor().getFullName();
        this.likeCount = blog.getLikeCount();
        this.image = blog.getImages();
        this.authorAvatar = blog.getAuthor().getProfilePicture(); 
    }

    // Getters and setters (optional if using lombok)
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
}
