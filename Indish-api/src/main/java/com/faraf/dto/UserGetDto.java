package com.faraf.dto;


import java.time.LocalDateTime;


public class UserGetDto {

    private Long id;
    private String userName;
    private String email;
    private String country;
    private String city;
    private String password;
    private String bio;
    private String avatar;
    private LocalDateTime create_date;
    private LocalDateTime modified_date;

    public UserGetDto() {
    }

    public UserGetDto(Long id, String userName, String email, String country, String city, String password, String bio, String avatar, LocalDateTime create_date, LocalDateTime modified_date) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.country = country;
        this.city = city;
        this.password = password;
        this.bio = bio;
        this.avatar = avatar;
        this.create_date = create_date;
        this.modified_date = modified_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
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
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", avatar='" + avatar + '\'' +
                ", create_date=" + create_date +
                ", modified_date=" + modified_date +
                '}';
    }
}
