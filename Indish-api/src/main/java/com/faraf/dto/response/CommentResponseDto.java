package com.faraf.dto.response;

public class CommentResponseDto {

    private Long commentId;
    private Long userId;
    private Long foodId;
    private String content;

    public CommentResponseDto() {
    }

    public CommentResponseDto(Long commentId, Long userId, Long foodId, String content) {
        this.commentId = commentId;
        this.userId = userId;
        this.foodId = foodId;
        this.content = content;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
