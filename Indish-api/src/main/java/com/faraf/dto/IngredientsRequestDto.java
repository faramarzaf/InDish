package com.faraf.dto;

public class IngredientsRequestDto {

    private Long id;
    private Long foodId;
    private String content;

    public IngredientsRequestDto() {
    }

    public IngredientsRequestDto(Long id, Long foodId, String content) {
        this.id = id;
        this.foodId = foodId;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
