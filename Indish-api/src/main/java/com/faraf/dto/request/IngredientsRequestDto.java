package com.faraf.dto.request;

public class IngredientsRequestDto extends BaseRequestDto{

    private String content;

    public IngredientsRequestDto() {
    }

    public IngredientsRequestDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
