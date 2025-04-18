package ut.edu.teammatching.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ut.edu.teammatching.dto.FriendDTO;
import ut.edu.teammatching.enums.FriendStatus;
import ut.edu.teammatching.exceptions.FriendRequestAlreadyExistsException;
import ut.edu.teammatching.exceptions.FriendRequestNotFoundException;
import ut.edu.teammatching.exceptions.UserNotFoundException;
import ut.edu.teammatching.models.Friend;
import ut.edu.teammatching.models.User;
import ut.edu.teammatching.repositories.FriendRepository;
import ut.edu.teammatching.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // Gửi yêu cầu kết bạn
    public FriendDTO sendFriendRequest(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

        Optional<Friend> existingRequest = friendRepository.findByRequesterAndReceiver(sender, receiver);
        if (existingRequest.isPresent()) {
            if (existingRequest.get().getStatus() == FriendStatus.PENDING) {
                throw new FriendRequestAlreadyExistsException("Friend request already exists and is pending");
            } else if (existingRequest.get().getStatus() == FriendStatus.ACCEPTED) {
                throw new FriendRequestAlreadyExistsException("You are already friends");
            }
        }

        Friend friend = new Friend();
        friend.setRequester(sender);
        friend.setReceiver(receiver);
        friend.setStatus(FriendStatus.PENDING);

        Friend savedFriend = friendRepository.save(friend);

        // Trả về FriendDTO thay vì Friend
        return new FriendDTO(savedFriend);
    }

    // Phản hồi yêu cầu kết bạn
    public FriendDTO respondToFriendRequest(Long requestId, boolean accept) {
        Friend friend = friendRepository.findById(requestId)
                .orElseThrow(() -> new FriendRequestNotFoundException("Friend request not found"));
        friend.setStatus(accept ? FriendStatus.ACCEPTED : FriendStatus.REJECTED);
        Friend updatedFriend = friendRepository.save(friend);

        // Trả về FriendDTO thay vì Friend
        return new FriendDTO(updatedFriend);
    }

    // Lấy danh sách bạn bè
    public List<FriendDTO> getFriendList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Friend> friends = friendRepository.findByRequesterOrReceiverAndStatus(user, user, FriendStatus.ACCEPTED);
        return friends.stream()
                .map(FriendDTO::new)  // Ánh xạ Friend thành FriendDTO
                .collect(Collectors.toList());
    }

    // Lấy danh sách bạn bè đã xác nhận
    public List<FriendDTO> getAcceptedFriends(Long userId) {
        List<Friend> friends = friendRepository.findAcceptedFriends(userId);
        return friends.stream()
                .map(FriendDTO::new)  // Ánh xạ Friend thành FriendDTO
                .collect(Collectors.toList());
    }

    // Lấy danh sách yêu cầu kết bạn đang chờ xử lý
    public List<FriendDTO> getPendingRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Friend> pendingFriends = friendRepository.findByReceiverAndStatus(user, FriendStatus.PENDING);

        return pendingFriends.stream()
                .map(FriendDTO::new)  // Ánh xạ Friend thành FriendDTO
                .collect(Collectors.toList());
    }

    // Hủy kết bạn
    public void unfriend(Long friendId) {
        Friend friend = friendRepository.findById(friendId)
                .orElseThrow(() -> new FriendRequestNotFoundException("Friend relationship not found"));
        if (friend.getStatus() == FriendStatus.ACCEPTED) {
            friendRepository.delete(friend);
        } else {
            throw new IllegalStateException("You can only unfriend accepted friends");
        }
    }

    // Lấy số lượng bạn bè
    public Long getFriendsCount(Long userId) {
        return friendRepository.countByRequesterIdAndStatus(userId, FriendStatus.ACCEPTED) +
                friendRepository.countByReceiverIdAndStatus(userId, FriendStatus.ACCEPTED);
    }

    // Kiểm tra xem 2 người có phải là bạn hay không
    public boolean isFriend(Long userId1, Long userId2) {
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        User user2 = userRepository.findById(userId2)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return friendRepository.findByRequesterAndReceiver(user1, user2)
                .filter(f -> f.getStatus() == FriendStatus.ACCEPTED)
                .isPresent()
                || friendRepository.findByRequesterAndReceiver(user2, user1)
                .filter(f -> f.getStatus() == FriendStatus.ACCEPTED)
                .isPresent();
    }
}
