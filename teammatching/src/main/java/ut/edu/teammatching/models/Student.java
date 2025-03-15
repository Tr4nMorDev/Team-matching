package ut.edu.teammatching.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
public class Student extends User {

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "major", nullable = false)
    private String major;

    @Column(name = "term", nullable = false)
    private Integer term;

// Thêm các thuộc tính riêng của Student nếu có
}
