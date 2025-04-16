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
    @Autowired private MessageService messageService;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageDTO messageDTO, Principal principal) {
        Long senderId = Long.parseLong(principal.getName());

        Message savedMessage;

        if (messageDTO.getTeamId() != null) {
            savedMessage = messageService.sendTeamMessage(senderId, messageDTO.getTeamId(), messageDTO.getContent());
            messagingTemplate.convertAndSend("/topic/team." + messageDTO.getTeamId(), savedMessage);
        } else if (messageDTO.getReceiverId() != null) {
            savedMessage = messageService.sendPrivateMessage(senderId, messageDTO.getReceiverId(), messageDTO.getContent());
            messagingTemplate.convertAndSendToUser(messageDTO.getReceiverId().toString(), "/queue/private", savedMessage);
        }
    }
}
