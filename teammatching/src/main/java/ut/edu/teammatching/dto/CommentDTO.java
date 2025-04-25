package ut.edu.teammatching.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private String authorName; // Chỉ lấy tên người dùng
    private String authorAvatar; // Thêm ảnh đại diện của người dùng

    // Constructor bao gồm ảnh đại diện của người dùng
    public CommentDTO(Long id, String content, String authorName, String authorAvatar) {
        this.id = id;
        this.content = content;
        this.authorName = authorName;
        this.authorAvatar = authorAvatar;
    }
}
