package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ut.edu.teammatching.models.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.id = :user1 AND m.receiver.id = :user2) OR " +
            "(m.sender.id = :user2 AND m.receiver.id = :user1) " +
            "ORDER BY m.sentAt ASC")
    List<Message> findBySenderIdAndReceiverIdInBothDirections(@Param("user1") Long user1,
                                                              @Param("user2") Long user2);

    // Get team chat history by team ID
    List<Message> findByTeamIdOrderBySentAtAsc(Long teamId);
}