package com.faraf.dto;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class UserGetDto {

    private String userName;
    private String email;
    private String country;
    private String city;
    private String bio;
    private byte[] avatar;
    private List<FoodPostDto> posts = new ArrayList<>();
    private LocalDateTime create_date;
    private LocalDateTime modified_date;

    public UserGetDto() {
    }

    public UserGetDto(String userName, String email, String country, String city, String bio, byte[] avatar, List<FoodPostDto> posts, LocalDateTime create_date, LocalDateTime modified_date) {
        this.userName = userName;
        this.email = email;
        this.country = country;
        this.city = city;
        this.bio = bio;
        this.avatar = avatar;
        this.posts = posts;
        this.create_date = create_date;
        this.modified_date = modified_date;
    }


    public LocalDateTime getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDateTime modified_date) {
        this.modified_date = modified_date;
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


    public List<FoodPostDto> getPosts() {
        return posts;
    }

    public void setPosts(List<FoodPostDto> posts) {
        this.posts = posts;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    @Override
    public String toString() {
        return "UserGetDto{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", bio='" + bio + '\'' +
                ", avatar='" + avatar + '\'' +
                ", posts=" + posts +
                ", create_date=" + create_date +
                ", modified_date=" + modified_date +
                '}';
    }
}
