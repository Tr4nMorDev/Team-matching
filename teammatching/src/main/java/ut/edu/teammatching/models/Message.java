package ut.edu.teammatching.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId", nullable = false)
    private Long id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_student_id")
    private Student senderStudent;

    @ManyToOne
    @JoinColumn(name = "sender_lecturer-id")
    private Lecturer senderLecturer;

    @ManyToOne
    @JoinColumn(name = "receiver_student_id")
    private Student receiverStudent;

    @ManyToOne
    @JoinColumn(name = "receiver_lecturer_id")
    private Lecturer receiverLecturer;
}