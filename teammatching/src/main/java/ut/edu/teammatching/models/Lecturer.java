package ut.edu.teammatching.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lecturer extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecturer_id")
    private Long id;

    @Column(name = "department", nullable = false)
    private String department;

    @Lob
    @Column(name = "ResearchAreas")
    private String researchAreas;

    @OneToMany(mappedBy = "lecturer")
    private List<Team> supervisedTeams = new ArrayList<>();

    @OneToMany(mappedBy = "lecturer")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "lecturer")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "lecturer")
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "ratedLecturer")
    private List<Rating> receivedRatings = new ArrayList<>();

    @OneToMany(mappedBy = "givenByLecturer")
    private List<Rating> givenRatings = new ArrayList<>();

    @OneToMany(mappedBy = "assignedToLecturer")
    private List<Task> assignedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "senderLecturer")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiverLecturer")
    private List<Message> receivedMessages = new ArrayList<>();
}