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

    private String bio;

    private String city;

    private String country;

    private String  avatar;

    private List<FoodPostRequestDto> posts = new ArrayList<>();

    public UserPostDto() {
    }

    public UserPostDto(String userName, String email, String password, String bio, String city, String country,String avatar, List<FoodPostRequestDto> posts) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.city = city;
        this.country = country;
        this.posts = posts;
        this.avatar = avatar;
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

    public List<FoodPostRequestDto> getPosts() {
        return posts;
    }

    public void setPosts(List<FoodPostRequestDto> posts) {
        this.posts = posts;
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


    @Override
    public String toString() {
        return "UserPostDto{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", avatar='" + avatar + '\'' +
                ", posts=" + posts +
                '}';
    }
}
