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
@Table(name="lecturer")
public class Lecturer extends User {
    @Column(name = "department", nullable = false)
    private String department;

    @Lob
    @Column(name = "ResearchAreas")
    private String researchAreas;

    @OneToMany(mappedBy = "lecturer")
    private List<Team> supervisedTeams = new ArrayList<>();
}