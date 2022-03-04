package com.faraf.dto;

import java.time.LocalDateTime;

public class FoodPostResponseDto {

    private Long id;
    private String name;
    private String description;
    private String originCountry;
    private int timeRequired;
    private boolean isVeganFood;

    private LocalDateTime created_date;
    private LocalDateTime modified_date;
    private UserGetDto user;


    public FoodPostResponseDto(Long id, String name, String description, String originCountry, int timeRequired, boolean isVeganFood, LocalDateTime created_date, LocalDateTime modified_date, UserGetDto user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.originCountry = originCountry;
        this.timeRequired = timeRequired;
        this.isVeganFood = isVeganFood;
        this.created_date = created_date;
        this.modified_date = modified_date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public int getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(int timeRequired) {
        this.timeRequired = timeRequired;
    }

    public boolean isVeganFood() {
        return isVeganFood;
    }

    public void setVeganFood(boolean veganFood) {
        isVeganFood = veganFood;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public LocalDateTime getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDateTime modified_date) {
        this.modified_date = modified_date;
    }

    public UserGetDto getUser() {
        return user;
    }

    public void setUser(UserGetDto user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "FoodPostDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", originCountry='" + originCountry + '\'' +
                ", timeRequired=" + timeRequired +
                ", isVeganFood=" + isVeganFood +
                ", created_date=" + created_date +
                ", modified_date=" + modified_date +
                ", user=" + user +
                '}';
    }
}
