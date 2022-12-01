package com.faraf.controller;

import com.faraf.dto.request.CommentRequestDto;
import com.faraf.dto.request.DeleteCommentRequestDto;
import com.faraf.dto.response.CommentResponseDto;
import com.faraf.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comment")
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/add")
    public CommentResponseDto addCommentToPost(@RequestBody CommentRequestDto commentRequestDto) {
        return commentService.addCommentToPost(commentRequestDto);
    }


    @GetMapping("/by-id")
    public CommentResponseDto findByCommentId(@RequestParam Long commentId) {
        return commentService.findByCommentId(commentId);
    }


    @GetMapping("/by-postId")
    public List<CommentResponseDto> findByFoodPostId(@RequestParam Long postId,
                                                     @RequestParam int pageNo,
                                                     @RequestParam int pageSize,
                                                     @RequestParam String sort) {
        return commentService.findByFoodPostId(postId, pageNo, pageSize, sort);
    }

    @GetMapping("/by-userId")
    public List<CommentResponseDto> findByUserId(@RequestParam Long userId,
                                                 @RequestParam int pageNo,
                                                 @RequestParam int pageSize,
                                                 @RequestParam String sort) {
        return commentService.findByUserId(userId, pageNo, pageSize, sort);
    }

    @DeleteMapping("/by-id")
    @PreAuthorize("#deleteCommentRequestDto.userId == authentication.principal.id")
    public void deleteByCommentId(@RequestBody DeleteCommentRequestDto deleteCommentRequestDto) {
        commentService.deleteByCommentId(deleteCommentRequestDto);
    }


}
