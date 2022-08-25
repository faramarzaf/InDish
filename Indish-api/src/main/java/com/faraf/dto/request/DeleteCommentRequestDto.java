package com.faraf.dto.request;

public class DeleteCommentRequestDto {

    private Long commentId;
    private Long userId;

    public DeleteCommentRequestDto() {
    }

    public DeleteCommentRequestDto(Long commentId, Long userId) {
        this.commentId = commentId;
        this.userId = userId;
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
}
