package ut.edu.teammatching.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ut.edu.teammatching.dto.FriendDTO;
import ut.edu.teammatching.models.Friend;
import ut.edu.teammatching.services.FriendService;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/request")
    public ResponseEntity<Friend> sendFriendRequest(@RequestParam Long senderId, @RequestParam Long receiverId) {
        return ResponseEntity.ok(friendService.sendFriendRequest(senderId, receiverId));
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<Friend> respondToRequest(@PathVariable Long id, @RequestParam boolean accept) {
        return ResponseEntity.ok(friendService.respondToFriendRequest(id, accept));
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<FriendDTO>> getAcceptedFriends(@PathVariable Long userId) {
        List<FriendDTO> friends = friendService.getAcceptedFriends(userId);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<Friend>> getPendingRequests(@PathVariable Long userId) {
        return ResponseEntity.ok(friendService.getPendingRequests(userId));
    }

    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> unfriend(@PathVariable Long friendId) {
        friendService.unfriend(friendId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getFriendsCount(@PathVariable Long userId) {
        return ResponseEntity.ok(friendService.getFriendsCount(userId));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isFriend(@RequestParam Long userId1, @RequestParam Long userId2) {
        return ResponseEntity.ok(friendService.isFriend(userId1, userId2));
    }
}
