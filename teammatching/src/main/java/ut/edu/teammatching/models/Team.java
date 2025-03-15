package ut.edu.teammatching.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
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

}