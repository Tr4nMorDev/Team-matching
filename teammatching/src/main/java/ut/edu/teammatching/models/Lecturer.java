package ut.edu.teammatching.models;

import jakarta.persistence.*;

@Entity
@Table(name = "lecturer")
public class Lecturer {
    @Id
    @Column(name = "userId")
    private Long id;

    @Column(nullable = false)
    private String department;

    @Column(name = "ResearchAreas", columnDefinition = "TEXT")
    private String researchAreas;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getResearchAreas() {
        return researchAreas;
    }

    public void setResearchAreas(String researchAreas) {
        this.researchAreas = researchAreas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.id = user.getId();
        }
    }
}