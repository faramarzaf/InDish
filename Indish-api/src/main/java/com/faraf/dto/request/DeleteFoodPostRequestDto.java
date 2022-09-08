package com.faraf.dto.request;

public class DeleteFoodPostRequestDto extends BaseRequestDto{

    private Long foodPostId;

    public DeleteFoodPostRequestDto() {
    }

    public void setFoodPostId(Long foodPostId) {
        this.foodPostId = foodPostId;
    }

    public Long getFoodPostId() {
        return foodPostId;
    }
}
