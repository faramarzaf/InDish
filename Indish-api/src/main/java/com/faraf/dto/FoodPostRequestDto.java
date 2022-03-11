package com.faraf.dto;

public class FoodPostRequestDto {

    private String name;
    private String description;
    private String originCountry;
    private int timeRequired;
    private boolean isVeganFood;

    public FoodPostRequestDto() {
    }

    public FoodPostRequestDto(String name, String description, String originCountry, int timeRequired, boolean isVeganFood) {
        this.name = name;
        this.description = description;
        this.originCountry = originCountry;
        this.timeRequired = timeRequired;
        this.isVeganFood = isVeganFood;
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

    public Boolean getIsVeganFood() {
        return isVeganFood;
    }

    public void setIsVeganFood(Boolean veganFood) {
        isVeganFood = veganFood;
    }


    @Override
    public String toString() {
        return "FoodPostRequestDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", originCountry='" + originCountry + '\'' +
                ", timeRequired=" + timeRequired +
                ", isVeganFood=" + isVeganFood +
                '}';
    }
}
