package ut.edu.teammatching.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId", nullable = false)
    private Long id;

    @Column(name = "sentAt", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant sentAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "senderId", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "receiverId")
    private User receiver;  // nullable để không bắt buộc khi gửi cho team

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "teamId")
    private Team team;  // nullable để không bắt buộc khi gửi cho người dùng

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructor helper method để xác định loại tin nhắn (Private hay Team)
    @Transient
    public String getType() {
        if (receiver != null && team == null) {
            return "PRIVATE";
        } else if (receiver == null && team != null) {
            return "TEAM";
        }
        return "UNKNOWN";  // Trường hợp không xác định
    }
}
