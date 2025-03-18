package ut.edu.teammatching.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamId", nullable = false)
    private Long id;

    @Column(name = "teamName", nullable = false)
    private String teamName;

    @Lob
    @Column(name = "teamType", nullable = false)
    private String teamType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "leaderId", nullable = false)
    private User leader;

    @ManyToMany(mappedBy = "teams")
    private List<Student> students = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @OneToMany(mappedBy = "team")
    private List<Task> tasks = new ArrayList<>();
}