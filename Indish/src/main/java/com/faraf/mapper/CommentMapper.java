package com.faraf.mapper;

import com.faraf.dto.request.CommentRequestDto;
import com.faraf.dto.response.CommentResponseDto;
import com.faraf.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(source = "commentRequestDto.postId", target = "post.id")
    @Mapping(source = "commentRequestDto.userId", target = "user.id")
    Comment toEntity(CommentRequestDto commentRequestDto);


    @Mapping(source = "comment.id", target = "commentId")
    @Mapping(source = "comment.post.id", target = "foodId")
    @Mapping(source = "comment.user.id", target = "userId")
    CommentResponseDto toDto(Comment comment);

    @Mapping(source = "comment.id", target = "commentId")
    @Mapping(source = "comment.post.id", target = "foodId")
    @Mapping(source = "comment.user.id", target = "userId")
    List<CommentResponseDto> toDto(List<Comment> comment);


}
