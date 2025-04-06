package ut.edu.teammatching.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private Long receiverId; // Nếu gửi tới 1 user
    private Long teamId;     // Nếu gửi tới team
    private String content;
}