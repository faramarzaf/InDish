package com.faraf.dto.response;

public class IngredientResponseDto {

    private Long id;
    private String content;

    public IngredientResponseDto() {
    }

    public IngredientResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
