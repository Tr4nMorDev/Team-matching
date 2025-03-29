package ut.edu.teammatching.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ut.edu.teammatching.enums.Gender;
import ut.edu.teammatching.enums.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "role")  // Phân biệt bằng role
@JsonSubTypes({
        @JsonSubTypes.Type(value = Student.class, name = "STUDENT"),
        @JsonSubTypes.Type(value = Lecturer.class, name = "LECTURER")
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED) // Kế thừa với bảng riêng cho từng subclass
@Table(name="users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @NotBlank //rang buoc du lieu dau vao
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Setter(AccessLevel.NONE) //Khong tu dong tao setter
    @NotBlank
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "fullName")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Email
    @NotBlank
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    @BatchSize(size = 10) // Hạn chế số lượng join
    private List<String> skills = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_hobbies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "hobby")
    @BatchSize(size = 10)
    private List<String> hobbies = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_projects", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "project")
    @BatchSize(size = 10)
    private List<String> projects = new ArrayList<>();

    @Pattern(regexp = "^(\\+?\\d{1,3})?\\d{10,12}$", message = "Số điện thoại không hợp lệ")
    @Column(name = "phoneNumber", length = 20, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore // Tránh lặp dữ liệu
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Blog> blogs = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Notification> receivedNotifications;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Notification> sentNotifications;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Message> receivedMessages = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    private Set<Team> teams = new HashSet<>();

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    @PrePersist
    @PreUpdate
    private void encryptPassword() {
        if (this.password != null && !this.password.startsWith("$2a$")) { // Kiểm tra nếu chưa mã hóa
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            this.password = encoder.encode(this.password);
        }
    }
}