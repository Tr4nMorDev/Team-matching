package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ut.edu.teammatching.enums.FriendStatus;
import ut.edu.teammatching.models.Friend;
import ut.edu.teammatching.models.User;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByRequesterOrReceiver(User requester, User receiver);
    Optional<Friend> findByRequesterAndReceiver(User requester, User receiver);
    List<Friend> findByReceiverAndStatus(User receiver, FriendStatus status);
    List<Friend> findByRequesterAndStatus(User requester, FriendStatus status);
    List<Friend> findByRequesterOrReceiverAndStatus(User user1, User user2, FriendStatus status);
    Long countByRequesterIdAndStatus(Long requesterId, FriendStatus status);
    Long countByReceiverIdAndStatus(Long receiverId, FriendStatus status);
    @Query("SELECT f FROM Friend f WHERE f.status = 'ACCEPTED' AND (f.requester.id = :userId OR f.receiver.id = :userId)")
    List<Friend> findAcceptedFriends(@Param("userId") Long userId);
}
