package ut.edu.teammatching.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "profile_info")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String gender;
    private String email;
    private String school;

    @ElementCollection
    private List<String> skills;

    @ElementCollection
    private List<String> hobbies;

    @ElementCollection
    private List<String> projects;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    // Constructors
    public Profile() {}

    public Profile(String fullName, String gender, String email, String school, List<String> skills, List<String> hobbies, List<String> projects, User user) {
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.school = school;
        this.skills = skills;
        this.hobbies = hobbies;
        this.projects = projects;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
