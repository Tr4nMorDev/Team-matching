package ut.edu.teammatching.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId", nullable = false)
    private Long id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Lob
    @Column(name = "images")
    private String images;

    @Lob
    @Column(name = "videos")
    private String videos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "authorId", nullable = false)
    private User author;

}