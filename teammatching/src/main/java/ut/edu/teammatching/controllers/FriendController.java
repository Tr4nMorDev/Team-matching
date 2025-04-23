package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.dto.FriendDTO;
import ut.edu.teammatching.services.FriendService;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/request/{senderId}/{receiverId}")
    public ResponseEntity<FriendDTO> sendFriendRequest(@PathVariable Long senderId, @PathVariable Long receiverId) {
        if (friendService.isFriend(senderId, receiverId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        FriendDTO friendDTO = friendService.sendFriendRequest(senderId, receiverId);
        return ResponseEntity.status(HttpStatus.CREATED).body(friendDTO);
    }

    // Phản hồi yêu cầu kết bạn
    @PostMapping("/{id}/respond")
    public ResponseEntity<FriendDTO> respondToRequest(@PathVariable Long id, @RequestParam boolean accept) {
        // Gọi service và trả về FriendDTO
        FriendDTO friendDTO = friendService.respondToFriendRequest(id, accept);
        return ResponseEntity.ok(friendDTO);
    }

    // Lấy danh sách bạn bè đã chấp nhận
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<FriendDTO>> getAcceptedFriends(@PathVariable Long userId) {
        List<FriendDTO> friends = friendService.getAcceptedFriends(userId);
        return ResponseEntity.ok(friends);
    }

    // Lấy danh sách yêu cầu kết bạn đang chờ
    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<FriendDTO>> getPendingRequests(@PathVariable Long userId) {
        List<FriendDTO> pendingRequests = friendService.getPendingRequests(userId);
        return ResponseEntity.ok(pendingRequests);
    }

    // Hủy kết bạn
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> unfriend(@PathVariable Long friendId) {
        friendService.unfriend(friendId);
        return ResponseEntity.noContent().build();
    }

    // Lấy số lượng bạn bè
    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getFriendsCount(@PathVariable Long userId) {
        Long count = friendService.getFriendsCount(userId);
        return ResponseEntity.ok(count);
    }

    // Kiểm tra bạn
    @GetMapping("/check/{userId1}/{userId2}")
    public ResponseEntity<Boolean> isFriend(@PathVariable Long userId1, @PathVariable Long userId2) {
        boolean isFriend = friendService.isFriend(userId1, userId2);
        return ResponseEntity.ok(isFriend);
    }
}
