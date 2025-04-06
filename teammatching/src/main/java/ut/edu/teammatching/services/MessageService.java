package ut.edu.teammatching.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.models.Message;
import ut.edu.teammatching.models.Team;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.repositories.MessageRepository;
import ut.edu.teammatching.repositories.TeamRepository;
import ut.edu.teammatching.repositories.UserRepository;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TeamRepository teamRepository;

    public Message sendPrivateMessage(Long senderId, Long receiverId, String content) {
        User sender = userRepository.findById(senderId).orElseThrow();
        User receiver = userRepository.findById(receiverId).orElseThrow();

        Message msg = new Message();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(content);
        msg.setUser(sender); // sender là người gửi

        return messageRepository.save(msg);
    }

    public Message sendTeamMessage(Long senderId, Long teamId, String content) {
        User sender = userRepository.findById(senderId).orElseThrow();
        Team team = teamRepository.findById(teamId).orElseThrow();

        Message msg = new Message();
        msg.setSender(sender);
        msg.setTeam(team);
        msg.setContent(content);
        msg.setUser(sender); // sender là người gửi

        return messageRepository.save(msg);
    }

    public List<Message> getPrivateHistory(Long userId1, Long userId2) {
        return messageRepository.getPrivateMessages(userId1, userId2);
    }

    public List<Message> getTeamHistory(Long teamId) {
        return messageRepository.findByTeam_IdOrderBySentAtAsc(teamId);
    }
}
