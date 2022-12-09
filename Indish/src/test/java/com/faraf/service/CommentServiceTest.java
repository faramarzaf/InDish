package com.faraf.service;

import com.faraf.BaseTestClass;
import com.faraf.dto.request.CommentRequestDto;
import com.faraf.dto.request.DeleteCommentRequestDto;
import com.faraf.dto.response.CommentResponseDto;
import com.faraf.entity.Comment;
import com.faraf.entity.FoodPost;
import com.faraf.entity.Ingredient;
import com.faraf.entity.User;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.CommentMapper;
import com.faraf.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CommentServiceTest extends BaseTestClass {

    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Spy
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    private User sampleUser;
    private FoodPost foodPost;
    private Comment sampleComment;

    @BeforeEach
    public void setup() {
        commentService = new CommentServiceImpl(
                commentRepository,
                commentMapper
        );
        sampleUser = getCorrectSampleUser();
        foodPost = getSampleVeganFoodPost(sampleUser);
        sampleComment = getSampleComment(sampleUser, foodPost);

    }

    @Test
    public void it_should_add_comment_to_foodPost() {
        long userId = 1L;
        long postId = 2L;
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setContent(sampleComment.getContent());
        commentRequestDto.setPostId(postId);
        commentRequestDto.setUserId(userId);
        when(commentMapper.toEntity(commentRequestDto)).thenReturn(sampleComment);
        CommentResponseDto commentResponseDto = commentService.addCommentToPost(commentRequestDto);
        verify(commentRepository, times(1)).save(sampleComment);
        assertNotNull(commentResponseDto);
    }

    @Test
    public void it_should_find_comment_by_id() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(sampleComment));
        CommentResponseDto byCommentId = commentService.findByCommentId(anyLong());
        assertNotNull(byCommentId);
        verify(commentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void it_should_throw_exception_find_comment_by_id() {
        long commentId = 1L;
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> commentService.findByCommentId(commentId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not found any comment with id:" + commentId);
    }

    @Test
    public void it_should_find_comments_by_foodId() {
        when(commentRepository.findAllByPost_Id(anyLong())).thenReturn(Collections.singletonList(sampleComment));
        List<CommentResponseDto> byFoodPostId = commentService.findByFoodPostId(anyLong());
        assertThat(byFoodPostId).isNotNull();
        assertThat(byFoodPostId).isNotEmpty();
        verify(commentRepository, times(1)).findAllByPost_Id(anyLong());
    }


    @Test
    public void it_should_throw_exception_find_comments_by_foodId() {
        long foodId = 1L;
        when(commentRepository.findAllByPost_Id(anyLong())).thenReturn(null);
        assertThatThrownBy(() -> commentService.findByFoodPostId(foodId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not found any comments with food post id:" + foodId);
    }

    @Test
    public void it_should_find_comments_by_foodId_paginated() {
        long foodId = 1L;
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        PageImpl<Comment> comments = new PageImpl<>(Collections.singletonList(sampleComment));
        when(commentRepository.findAllByPost_Id(foodId, pageable)).thenReturn(comments);
        List<CommentResponseDto> commentResponseDtoList = commentService.findByFoodPostId(foodId, 1, 10, "id");
        assertThat(commentResponseDtoList).isNotNull();
        assertThat(commentResponseDtoList).isNotEmpty();
        verify(commentRepository, times(1)).findAllByPost_Id(foodId, pageable);
    }

    @Test
    public void it_should_throw_exception_find_comments_by_foodId_paginated() {
        long commentId = 1L;
        long foodId = 1L;
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        when(commentRepository.findAllByPost_Id(commentId, pageable)).thenReturn(Page.empty());
        assertThatThrownBy(() -> commentService.findByFoodPostId(commentId, 1, 10, "id"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not found any comments with food post id:" + foodId);
    }

    @Test
    public void it_should_find_comments_by_userId_paginated() {
        long userId = 1L;
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        PageImpl<Comment> comments = new PageImpl<>(Collections.singletonList(sampleComment));
        when(commentRepository.findAllByUser_Id(userId, pageable)).thenReturn(comments);
        List<CommentResponseDto> commentResponseDtoList = commentService.findByUserId(userId, 1, 10, "id");
        assertThat(commentResponseDtoList).isNotNull();
        assertThat(commentResponseDtoList).isNotEmpty();
        verify(commentRepository, times(1)).findAllByUser_Id(userId, pageable);
    }

    @Test
    public void it_should_throw_exception_find_comments_by_userId_paginated() {
        long postId = 1L;
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        when(commentRepository.findAllByPost_Id(postId, pageable)).thenReturn(Page.empty());
        assertThatThrownBy(() -> commentService.findByFoodPostId(postId, 1, 10, "id"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not found any comments with food post id:" + postId);
    }


    @Test
    public void it_should_find_comments_by_userId() {
        when(commentRepository.findAllByUser_Id(anyLong())).thenReturn(Collections.singletonList(sampleComment));
        List<CommentResponseDto> commentResponseDtoList = commentService.findByUserId(anyLong());
        assertThat(commentResponseDtoList).isNotNull();
        assertThat(commentResponseDtoList).isNotEmpty();
        verify(commentRepository, times(1)).findAllByUser_Id(anyLong());
    }


    @Test
    public void it_should_throw_exception_find_comments_by_userId() {
        long userId = 1L;
        when(commentRepository.findAllByUser_Id(userId)).thenReturn(null);
        assertThatThrownBy(() -> commentService.findByUserId(userId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not found any comments with user id:" + userId);
    }


    @Test
    public void it_should_delete_comment_by_id() {
        long commentId = 1L;
        long userId = 2L;
        DeleteCommentRequestDto requestDto = new DeleteCommentRequestDto();
        requestDto.setUserId(userId);
        requestDto.setCommentId(commentId);
        doNothing().when(commentRepository).deleteById(requestDto.getCommentId());
        commentService.deleteByCommentId(requestDto);
        verify(commentRepository, times(1)).deleteById(requestDto.getCommentId());
    }


}
