package ut.edu.teammatching.models;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import ut.edu.teammatching.models.Profile;

@Entity
@Table (name = "user")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profileInfo;

    public User() {}
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public Profile getProfileInfo() {
        return profileInfo;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setProfileInfo(Profile profileInfo) {
        this.profileInfo = profileInfo;
    }
}
