package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.dto.MessageDTO;
import ut.edu.teammatching.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // lay lich su nhan tin giu 2 nguoi
    @GetMapping("/private")
    public ResponseEntity<List<MessageDTO>> getPrivateChat(
            @RequestParam Long user1,
            @RequestParam Long user2
    ) {
        List<MessageDTO> messages = messageService.getPrivateChatHistory(user1, user2);
        return ResponseEntity.ok(messages);
    }

    // lay lich su chat team bang id
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<MessageDTO>> getTeamChat(@PathVariable Long teamId) {
        List<MessageDTO> messages = messageService.getTeamChatHistory(teamId);
        return ResponseEntity.ok(messages);
    }
}