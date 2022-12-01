package com.faraf.service;

import com.faraf.dto.request.CommentRequestDto;
import com.faraf.dto.request.DeleteCommentRequestDto;
import com.faraf.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto addCommentToPost(CommentRequestDto commentRequestDto);

    CommentResponseDto findByCommentId(Long commentId);

    List<CommentResponseDto> findByFoodPostId(Long foodPostId, int pageNo, int pageSize, String sort);

    List<CommentResponseDto> findByUserId(Long userId, int pageNo, int pageSize, String sort);

    List<CommentResponseDto> findByFoodPostId(Long foodPostId);

    List<CommentResponseDto> findByUserId(Long userId);

    void deleteByCommentId(DeleteCommentRequestDto deleteCommentRequestDto);


}
