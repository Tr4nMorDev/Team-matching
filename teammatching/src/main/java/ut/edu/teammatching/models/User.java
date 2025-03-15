package ut.edu.teammatching.models;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ut.edu.teammatching.enums.Gender;
import ut.edu.teammatching.enums.Role;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass //Danh dau day la lop truu tuong dung chung cho cac enity con
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public abstract class User {

    @Column(name = "userName", nullable = false)
    protected String userName;

    @Column(name = "password", nullable = false)
    protected String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    protected Role role;

    @Column(name = "fullName")
    protected String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    protected Gender gender;

    @Column(name = "email")
    protected String email;

    @Lob
    @Column(name = "skills")
    protected String skills;

    @Lob
    @Column(name = "hobby")
    protected String hobby;

    @Lob
    @Column(name = "projects")
    protected String projects;

    @Column(name = "phoneNumber", length = 20)
    protected String phoneNumber;
}