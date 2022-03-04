package com.faraf.dto;

import java.util.ArrayList;
import java.util.List;

public class UserInfoUpdateRequestDto {

    private String bio;
    private String city;
    private String country;
    private String avatar;
    private List<FoodPostRequestDto> posts = new ArrayList<>();

    public UserInfoUpdateRequestDto() {
    }

    public UserInfoUpdateRequestDto(String bio, String city, String country, String avatar, List<FoodPostRequestDto> posts) {
        this.bio = bio;
        this.city = city;
        this.country = country;
        this.avatar = avatar;
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

    public List<FoodPostRequestDto> getPosts() {
        return posts;
    }

    public void setPosts(List<FoodPostRequestDto> posts) {
        this.posts = posts;
    }


    @Override
    public String toString() {
        return "UserInfoUpdateRequestDto{" +
                "bio='" + bio + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", avatar='" + avatar + '\'' +
                ", posts=" + posts +
                '}';
    }
}
