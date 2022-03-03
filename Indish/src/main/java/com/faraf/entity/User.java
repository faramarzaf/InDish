package com.faraf.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})})
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotEmpty(message = "{username.blank}")
    @Size(min = 3, max = 20, message = "{username.length}")
    private String userName;

    @Column(name = "email")
    @Email(message = "{email.valid}", regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @NotEmpty(message = "{email.blank}")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "{password.blank}")
    private String password;

    @CreationTimestamp
    @Column(name = "joined_on")
    private LocalDateTime joinedOn;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "up_id")
    private UserProfile userProfile;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fp_fid", referencedColumnName = "id")
    private List<FoodPost> posts = new ArrayList<>();

    public User() {
    }

    public User(Long id, String userName, String email, String password, LocalDateTime joinedOn, UserProfile userProfile, List<FoodPost> posts) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.joinedOn = joinedOn;
        this.userProfile = userProfile;
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(LocalDateTime joinedOn) {
        this.joinedOn = joinedOn;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public List<FoodPost> getPosts() {
        return posts;
    }

    public void setPosts(List<FoodPost> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", joinedOn=" + joinedOn +
                ", userProfile=" + userProfile +
                ", posts=" + posts +
                '}';
    }
}
