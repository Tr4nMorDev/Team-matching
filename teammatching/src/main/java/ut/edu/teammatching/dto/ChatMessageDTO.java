package ut.edu.teammatching.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {

    private Long receiverId;  // Nếu gửi tin nhắn đến một user
    private Long teamId;      // Nếu gửi tin nhắn đến một team
    private String content;   // Nội dung tin nhắn

    // Phương thức kiểm tra tính hợp lệ của tin nhắn
    public boolean isValid() {
        // Đảm bảo chỉ có một trong hai trường receiverId hoặc teamId là không null
        return (receiverId != null && teamId == null) || (receiverId == null && teamId != null);
    }
}
