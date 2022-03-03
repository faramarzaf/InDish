package com.faraf.dto;


import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Component
public class UserPostDto {

    @NotEmpty(message = "{username.blank}")
    @Size(min = 3, max = 20, message = "{username.length}")
    private String userName;

    @Email(message = "{email.valid}", regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @NotEmpty(message = "{email.blank}")
    private String email;

    @NotEmpty(message = "{password.blank}")
    private String password;

    private UserProfileDto userProfile;

    private List<FoodPostDto> posts = new ArrayList<>();

    public UserPostDto() {
    }

    public UserPostDto(String userName, String email, String password, UserProfileDto userProfile, List<FoodPostDto> posts) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userProfile = userProfile;
        this.posts = posts;
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

    @Override
    public String toString() {
        return "UserPostDto{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userProfile=" + userProfile +
                ", posts=" + posts +
                '}';
    }
}
