package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ut.edu.teammatching.models.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Lấy tin nhắn giữa 2 user
    @Query("SELECT m FROM Message m WHERE " +
            "((m.sender.id = :user1 AND m.receiver.id = :user2) OR " +
            "(m.sender.id = :user2 AND m.receiver.id = :user1)) " +
            "AND m.team IS NULL ORDER BY m.sentAt ASC")
    List<Message> getPrivateMessages(Long user1, Long user2);

    // Lấy tin nhắn trong team
    List<Message> findByTeam_IdOrderBySentAtAsc(Long teamId);
}

