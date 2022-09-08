package com.faraf.dto.request;

public class DeleteIngredientRequestDto extends BaseRequestDto{

    private Long ingredientId;

    public DeleteIngredientRequestDto() {
    }

    public DeleteIngredientRequestDto(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}
