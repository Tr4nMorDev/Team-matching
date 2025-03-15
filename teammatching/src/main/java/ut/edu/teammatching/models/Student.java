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

@Entity
@Table(name = "students")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Student extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(nullable = false)
    private String major; //chuyen nganh

    @Column(nullable = false)
    private Integer term;

    @Column(nullable = false)
    private Integer year;

    @ManyToMany
    @JoinTable(
            name = "student_team",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "ratedStudent")
    private List<Rating> receivedRatings = new ArrayList<>();

    @OneToMany(mappedBy = "givenByStudent")
    private List<Rating> givenRatings = new ArrayList<>();

    @OneToMany(mappedBy = "assignedToStudent")
    private List<Task> assignedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "senderStudent")
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiverStudent")
    private List<Message> receivedMessages = new ArrayList<>();
}
