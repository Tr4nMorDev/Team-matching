package ut.edu.teammatching.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ut.edu.teammatching.dto.ChatMessageDTO;
import ut.edu.teammatching.services.MessageService;
import ut.edu.teammatching.models.Message;
import java.security.Principal;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageDTO messageDTO, Principal principal) {
        Long senderId = Long.parseLong(principal.getName());

        if (messageDTO.getReceiverId() == null && messageDTO.getTeamId() == null) {
            // Trả về lỗi nếu không có receiver hoặc team
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/private", "Lỗi: Chưa xác định người nhận hoặc nhóm.");
            return;
        }

        Message savedMessage = null;
        try {
            // Gửi tin nhắn tới nhóm
            if (messageDTO.getTeamId() != null) {
                savedMessage = messageService.sendTeamMessage(senderId, messageDTO.getTeamId(), messageDTO.getContent());
                messagingTemplate.convertAndSend("/topic/team." + messageDTO.getTeamId(), savedMessage);
            }
            // Gửi tin nhắn tới người dùng riêng
            else if (messageDTO.getReceiverId() != null) {
                savedMessage = messageService.sendPrivateMessage(senderId, messageDTO.getReceiverId(), messageDTO.getContent());
                messagingTemplate.convertAndSendToUser(messageDTO.getReceiverId().toString(), "/queue/private", savedMessage);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ, ví dụ lỗi gửi tin nhắn
            messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/private", "Lỗi khi gửi tin nhắn: " + e.getMessage());
        }
    }
}
