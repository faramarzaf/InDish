package com.faraf.service;


import com.faraf.dto.request.*;
import com.faraf.dto.response.CommentResponseDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.dto.response.IngredientResponseDto;
import com.faraf.entity.FoodPost;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.FoodMapper;
import com.faraf.repository.FoodPostRepository;
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
public class FoodPostServiceImpl implements FoodPostService {


    private final FoodPostRepository foodPostRepository;
    private final FoodMapper foodMapper;
    private final IngredientService ingredientService;
    private final CommentService commentService;

    @Override
    public FoodPostResponseDto findById(long id) {
        Optional<FoodPost> optFoodPost = foodPostRepository.findById(id);
        if (optFoodPost.isPresent()) {
            FoodPost foodPost = optFoodPost.get();
            return foodMapper.toFoodPostResponseDto(foodPost);
        } else throw new NotFoundException("The food not found with id:" + id);
    }

    @Override
    @Transactional
    public FoodPostResponseDto addFoodToUser(FoodPostRequestDto requestDto) {
        FoodPost foodPost = foodMapper.toEntity(requestDto);
        foodPostRepository.save(foodPost);
        return foodMapper.toFoodPostResponseDto(foodPost);
    }

    @Override
    public List<FoodPostResponseDto> findAllByUserId(Long userId) {
        List<FoodPost> allPostsByUserId = foodPostRepository.findAllByUser_Id(userId);
        return foodMapper.toFoodPostResponseDto(allPostsByUserId);
    }

    @Override
    public List<FoodPostResponseDto> findAllByUsername(String username, int pageNo, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<FoodPost> allPostsByUsername = foodPostRepository.findByUser_UserName(username, pageable);
        return foodMapper.toFoodPostResponseDto(allPostsByUsername.getContent());
    }

    @Override
    public List<FoodPostResponseDto> findAllByUserId(Long userId, int pageNo, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<FoodPost> allPostsByUserId = foodPostRepository.findAllByUser_Id(userId, pageable);
        return foodMapper.toFoodPostResponseDto(allPostsByUserId.getContent());
    }

    @Override
    public List<FoodPostResponseDto> findAllByOriginCountry(String country, int pageNo, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<FoodPost> allByOriginCountry = foodPostRepository.findAllByOriginCountry(country, pageable);
        return foodMapper.toFoodPostResponseDto(allByOriginCountry.getContent());
    }

    @Override
    public List<FoodPostResponseDto> findAllByVeganFoodTrue(int pageNo, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<FoodPost> allByVeganFoodTrue = foodPostRepository.findAllByVeganFoodTrue(pageable);
        return foodMapper.toFoodPostResponseDto(allByVeganFoodTrue.getContent());
    }

    @Override
    public List<FoodPostResponseDto> findAllByVeganFoodFalse(int pageNo, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<FoodPost> allByVeganFoodFalse = foodPostRepository.findAllByVeganFoodFalse(pageable);
        return foodMapper.toFoodPostResponseDto(allByVeganFoodFalse.getContent());
    }

    @Override
    public List<FoodPostResponseDto> findAllByTimeRequiredEquals(int hour, int pageNo, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<FoodPost> allByTimeRequiredEquals = foodPostRepository.findAllByTimeRequiredEquals(hour, pageable);
        return foodMapper.toFoodPostResponseDto(allByTimeRequiredEquals.getContent());
    }

    @Override
    public List<FoodPostResponseDto> findAllByTimeRequiredBetween(int startHour, int endHour, int pageNo, int pageSize, String sort) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Page<FoodPost> allByTimeRequiredBetween = foodPostRepository.findAllByTimeRequiredBetween(startHour, endHour, pageable);
        return foodMapper.toFoodPostResponseDto(allByTimeRequiredBetween.getContent());
    }

    @Override
    @Transactional
    public void updateFoodPost(FoodPostUpdateRequestDto requestDto) {
        FoodPostResponseDto foodPostResponseDto = findById(requestDto.getFoodPostId());
        FoodPost foodPostById = foodMapper.toEntity(foodPostResponseDto);
        foodPostById.setName(requestDto.getName());
        foodPostById.setDescription(requestDto.getDescription());
        foodPostById.setOriginCountry(requestDto.getOriginCountry());
        foodPostById.setTimeRequired(requestDto.getTimeRequired());
        foodPostById.setVeganFood(requestDto.isVeganFood());
        foodPostRepository.save(foodPostById);
    }

    @Override
    @Transactional
    public void deletePostByFoodId(DeleteFoodPostRequestDto requestDto) {
        removeFoodChildren(requestDto);
        foodPostRepository.deleteById(requestDto.getFoodPostId());
    }

    private void removeFoodChildren(DeleteFoodPostRequestDto requestDto) {
        List<IngredientResponseDto> allIngredientsByFoodId = ingredientService.getIngredientsByFoodId(requestDto.getFoodPostId());
        List<CommentResponseDto> allCommentsByFoodId = commentService.findByFoodPostId(requestDto.getFoodPostId());

        for (IngredientResponseDto ingredientResponseDto : allIngredientsByFoodId) {
            DeleteIngredientRequestDto deleteIngredientRequestDto = new DeleteIngredientRequestDto();
            deleteIngredientRequestDto.setUserId(requestDto.getUserId());
            deleteIngredientRequestDto.setIngredientId(ingredientResponseDto.getId());
            ingredientService.deleteByIngredientId(deleteIngredientRequestDto);
        }

        for (CommentResponseDto commentResponseDto : allCommentsByFoodId) {
            DeleteCommentRequestDto deleteCommentRequestDto = new DeleteCommentRequestDto();
            deleteCommentRequestDto.setUserId(requestDto.getUserId());
            deleteCommentRequestDto.setCommentId(commentResponseDto.getCommentId());
            commentService.deleteByCommentId(deleteCommentRequestDto);
        }
    }

}
