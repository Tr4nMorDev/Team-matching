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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    // Gửi tin nhắn cá nhân
    public Message sendPrivateMessage(Long senderId, Long receiverId, String content) {
        // Kiểm tra xem sender và receiver có khác nhau không
        if (senderId.equals(receiverId)) {
            throw new RuntimeException("Không thể gửi tin nhắn cho chính mình");
        }

        // Kiểm tra nếu người dùng không tồn tại
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người gửi với ID: " + senderId));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người nhận với ID: " + receiverId));

        // Kiểm tra nội dung tin nhắn không rỗng
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Nội dung tin nhắn không thể trống");
        }

        // Tạo và lưu tin nhắn
        Message msg = new Message();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(content);
        msg.setUser(sender); // sender là người gửi

        return messageRepository.save(msg);
    }

    // Gửi tin nhắn nhóm
    public Message sendTeamMessage(Long senderId, Long teamId, String content) {
        // Kiểm tra nếu người dùng không tồn tại
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người gửi với ID: " + senderId));

        // Kiểm tra nếu nhóm không tồn tại
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm với ID: " + teamId));

        // Kiểm tra người gửi có phải là thành viên của nhóm không
        if (!team.getStudents().contains(sender)) {
            throw new RuntimeException("Người gửi không phải là thành viên của nhóm");
        }

        // Kiểm tra nội dung tin nhắn không rỗng
        if (content == null || content.trim().isEmpty()) {
            throw new RuntimeException("Nội dung tin nhắn không thể trống");
        }

        // Tạo và lưu tin nhắn
        Message msg = new Message();
        msg.setSender(sender);
        msg.setTeam(team);
        msg.setContent(content);
        msg.setUser(sender); // sender là người gửi

        return messageRepository.save(msg);
    }

    // Lấy lịch sử tin nhắn giữa 2 user
    public List<Message> getPrivateHistory(Long userId1, Long userId2) {
        return messageRepository.getPrivateMessages(userId1, userId2);
    }

    // Lấy lịch sử tin nhắn trong nhóm
    public List<Message> getTeamHistory(Long teamId) {
        return messageRepository.findByTeam_IdOrderBySentAtAsc(teamId);
    }
}
