package com.faraf.dto;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class UserGetDto {

    private Long id;
    private String userName;
    private String email;
    private String password;
    private UserProfileDto userProfile;
    private List<FoodPostDto> posts = new ArrayList<>();
    private LocalDateTime joinedOn;

    public UserGetDto() {
    }

    public UserGetDto(Long id, String userName, String email, String password, UserProfileDto userProfile, List<FoodPostDto> posts, LocalDateTime joinedOn) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userProfile = userProfile;
        this.posts = posts;
        this.joinedOn = joinedOn;
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

    public UserProfileDto getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDto userProfile) {
        this.userProfile = userProfile;
    }

    public List<FoodPostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<FoodPostDto> posts) {
        this.posts = posts;
    }

    public LocalDateTime getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(LocalDateTime joinedOn) {
        this.joinedOn = joinedOn;
    }

    @Override
    public String toString() {
        return "UserGetDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userProfile=" + userProfile +
                ", posts=" + posts +
                ", joinedOn=" + joinedOn +
                '}';
    }
}
