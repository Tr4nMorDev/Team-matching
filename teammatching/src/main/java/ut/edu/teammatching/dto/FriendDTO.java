package ut.edu.teammatching.dto;

import lombok.Data;
import ut.edu.teammatching.enums.FriendStatus;
import ut.edu.teammatching.models.Friend;

import java.time.LocalDateTime;

@Data
public class FriendDTO {
    private Long id;
    private FriendStatus status;
    private LocalDateTime createdAt;
    private UserDTO requester;
    private UserDTO receiver;

    public FriendDTO(Friend friend) {
        this.id = friend.getId();
        this.status = friend.getStatus();
        this.createdAt = friend.getCreatedAt();
        this.requester = new UserDTO(friend.getRequester());
        this.receiver = new UserDTO(friend.getReceiver());
    }
}
