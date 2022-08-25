package com.faraf.controller;

import com.faraf.dto.request.CommentRequestDto;
import com.faraf.dto.response.CommentResponseDto;
import com.faraf.service.CommentService;
import lombok.RequiredArgsConstructor;
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
    public List<CommentResponseDto> findByFoodPostId(@RequestParam Long postId) {
        return commentService.findByFoodPostId(postId);
    }

    @GetMapping("/by-userId")
    public List<CommentResponseDto> findByUserId(@RequestParam Long userId) {
        return commentService.findByUserId(userId);
    }

    @DeleteMapping("/by-id")
    public void deleteByCommentId(@RequestParam Long commentId) {
        commentService.deleteByCommentId(commentId);
    }


}
