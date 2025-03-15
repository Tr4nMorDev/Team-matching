package ut.edu.teammatching.models;
import ut.edu.teammatching.enums.TaskStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskId", nullable = false)
    private Long id;

    @Column(name = "taskName", nullable = false)
    private String taskName;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status = TaskStatus.IN_PROGRESS;

    @Column(name = "deadline")
    private LocalDate deadline;

    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "assigned_to_student_id")
    private Student assignedToStudent;

    @ManyToOne
    @JoinColumn(name = "assigned_to_lecturer_id")
    private Lecturer assignedToLecturer;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}