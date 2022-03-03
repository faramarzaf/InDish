package com.faraf.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}, name = "uk_username"),
        @UniqueConstraint(columnNames = {"email"}, name = "uk_email")
})
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

    @Column(name = "bio")
    private String bio;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "avatar")
    private String avatar;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime create_date;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modified_date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fp_fid", referencedColumnName = "id")
    private List<FoodPost> posts = new ArrayList<>();

    public User() {
    }

    public User(Long id, String userName, String email, String password, String bio, String city, String country, String avatar, LocalDateTime create_date, LocalDateTime modified_date, List<FoodPost> posts) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.city = city;
        this.country = country;
        this.avatar = avatar;
        this.create_date = create_date;
        this.modified_date = modified_date;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime joinedOn) {
        this.create_date = joinedOn;
    }


    public List<FoodPost> getPosts() {
        return posts;
    }

    public void setPosts(List<FoodPost> posts) {
        this.posts = posts;
    }

    public LocalDateTime getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDateTime modified_date) {
        this.modified_date = modified_date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", avatar='" + avatar + '\'' +
                ", joinedOn=" + create_date +
                ", posts=" + posts +
                '}';
    }
}
