package com.faraf.dto;

import java.time.LocalDateTime;

public class FoodPostDto {

    private Long id;
    private String name;
    private String description;
    private String originCountry;
    private int timeRequired;
    private boolean isVeganFood;
    private LocalDateTime createdAt;
    private UserGetDto user;


    public FoodPostDto(Long id, String name, String description, String originCountry, int timeRequired, boolean isVeganFood, LocalDateTime createdAt, UserGetDto user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.originCountry = originCountry;
        this.timeRequired = timeRequired;
        this.isVeganFood = isVeganFood;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
