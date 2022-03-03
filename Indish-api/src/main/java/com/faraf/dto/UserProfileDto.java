package com.faraf.dto;

public class UserProfileDto {

    private Long id;
    private String bio;
    private String city;
    private String country;
    private UserGetDto user;

    public UserProfileDto() {
    }

    public UserProfileDto(Long id, String bio, String city, String country, UserGetDto user) {
        this.id = id;
        this.bio = bio;
        this.city = city;
        this.country = country;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserGetDto getUser() {
        return user;
    }

    public void setUser(UserGetDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserProfileDto{" +
                "id=" + id +
                ", bio='" + bio + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", user=" + user +
                '}';
    }
}
