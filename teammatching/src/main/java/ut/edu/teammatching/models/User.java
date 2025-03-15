package ut.edu.teammatching.models;
import ut.edu.teammatching.enums.Gender;
import ut.edu.teammatching.enums.Role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Kế thừa với bảng riêng cho từng subclass
@Table(name="User")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Long id;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "fullName")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "skills")
    private String skills;

    @Lob
    @Column(name = "hobby")
    private String hobby;

    @Lob
    @Column(name = "projects")
    private String projects;

    @Column(name = "phoneNumber", length = 20)
    private String phoneNumber;
}