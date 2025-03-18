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
@Table(name="student")
public class Student extends User {
    @Column(name = "major", nullable = false)
    private String major;

    @Column(name = "term", nullable = false)
    private Integer term;

    @ManyToMany
    @JoinTable(
            name = "student_team",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "assignedToStudent")
    private List<Task> assignedTasks = new ArrayList<>();

// Thêm các thuộc tính riêng của Student nếu có
}
