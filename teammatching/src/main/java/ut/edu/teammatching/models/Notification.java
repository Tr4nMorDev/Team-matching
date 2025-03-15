package ut.edu.teammatching.models;
import ut.edu.teammatching.enums.NotificationType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ut.edu.teammatching.enums.Gender;

import java.time.Instant;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificationId", nullable = false)
    private Long id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "recipientId", nullable = false)
    private User recipient;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "createdAt")
    private Instant createdAt;

    @ColumnDefault("0")
    @Column(name = "isRead")
    private Boolean isRead;

}