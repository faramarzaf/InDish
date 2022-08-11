package com.faraf.dto;

public class IngredientsResponseDto {

    private String content;

    public IngredientsResponseDto() {
    }

    public IngredientsResponseDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
