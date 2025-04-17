package ut.edu.teammatching.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ut.edu.teammatching.enums.FriendStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();
}
