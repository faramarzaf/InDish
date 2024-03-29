package com.faraf.dto.request;

import javax.validation.constraints.NotEmpty;

public class FoodPostUpdateRequestDto {

    @NotEmpty(message = "{userId.blank}")
    private Long userId;
    private Long foodPostId;
    private String name;
    private String description;
    private String originCountry;
    private int timeRequired;
    private boolean veganFood;

    public FoodPostUpdateRequestDto() {
    }

    public FoodPostUpdateRequestDto(@NotEmpty(message = "{userId.blank}") Long userId, Long foodPostId, String name, String description, String originCountry, int timeRequired, boolean veganFood) {
        this.userId = userId;
        this.foodPostId = foodPostId;
        this.name = name;
        this.description = description;
        this.originCountry = originCountry;
        this.timeRequired = timeRequired;
        this.veganFood = veganFood;
    }

    public Long getFoodPostId() {
        return foodPostId;
    }

    public void setFoodPostId(Long foodPostId) {
        this.foodPostId = foodPostId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return veganFood;
    }

    public void setVeganFood(boolean veganFood) {
        this.veganFood = veganFood;
    }
}
