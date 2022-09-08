package com.faraf.dto.request;

public class BaseRequestDto {

    private Long userId;

    public BaseRequestDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
