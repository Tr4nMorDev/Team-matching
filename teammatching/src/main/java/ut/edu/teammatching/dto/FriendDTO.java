package ut.edu.teammatching.dto;

import lombok.Data;
import ut.edu.teammatching.models.Friend;

import java.time.LocalDateTime;

@Data
public class FriendDTO {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private Long requesterId;
    private String requesterName;
    private Long receiverId;
    private String receiverName;

    public FriendDTO(Friend friend) {
        this.id = friend.getId();
        this.status = friend.getStatus().name();
        this.createdAt = friend.getCreatedAt();
        this.requesterId = friend.getRequester().getId();
        this.requesterName = friend.getRequester().getFullName();
        this.receiverId = friend.getReceiver().getId();
        this.receiverName = friend.getReceiver().getFullName();
    }
}
