package com.faraf.service;


import com.faraf.dto.request.CommentRequestDto;
import com.faraf.dto.request.DeleteCommentRequestDto;
import com.faraf.dto.response.CommentResponseDto;
import com.faraf.entity.Comment;
import com.faraf.mapper.CommentMapper;
import com.faraf.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    @Override
    @Transactional
    public CommentResponseDto addCommentToPost(CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.toEntity(commentRequestDto);
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public CommentResponseDto findByCommentId(Long commentId) {
        Optional<Comment> optCommentById = commentRepository.findById(commentId);
        if (optCommentById.isPresent()) {
            Comment comment = optCommentById.get();
            return commentMapper.toDto(comment);
        }
        return null;
    }

    @Override
    public List<CommentResponseDto> findByFoodPostId(Long foodPostId, int pageNo, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<Comment> allCommentsByPostId = commentRepository.findAllByPost_Id(foodPostId, pageable);
        return commentMapper.toDto(allCommentsByPostId.getContent());
    }

    @Override
    public List<CommentResponseDto> findByUserId(Long userId, int pageNo, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<Comment> allCommentsByUserId = commentRepository.findAllByUser_Id(userId, pageable);
        return commentMapper.toDto(allCommentsByUserId.getContent());
    }

    @Override
    public List<CommentResponseDto> findByFoodPostId(Long foodPostId) {
        List<Comment> allCommentsByPostId = commentRepository.findAllByPost_Id(foodPostId);
        return commentMapper.toDto(allCommentsByPostId);
    }

    @Override
    public List<CommentResponseDto> findByUserId(Long userId) {
        List<Comment> allCommentsByUserId = commentRepository.findAllByUser_Id(userId);
        return commentMapper.toDto(allCommentsByUserId);
    }

    @Override
    @Transactional
    public void deleteByCommentId(DeleteCommentRequestDto deleteCommentRequestDto) {
        commentRepository.deleteById(deleteCommentRequestDto.getCommentId());
    }

}
