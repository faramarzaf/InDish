package com.faraf.dto;

public class IngredientsRequestDto {

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
