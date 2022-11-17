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
    public List<FoodPostResponseDto> findAllByUsername(String username) {
        List<FoodPost> allByUser_userName = foodPostRepository.findByUser_UserName(username);
        return foodMapper.toFoodPostResponseDto(allByUser_userName);
    }

    @Override
    public List<FoodPostResponseDto> findAllByUserId(Long userId) {
        List<FoodPost> allByUser_id = foodPostRepository.findAllByUser_Id(userId);
        return foodMapper.toFoodPostResponseDto(allByUser_id);
    }

    @Override
    public List<FoodPostResponseDto> findAllByOriginCountry(String country) {
        return foodMapper.toFoodPostResponseDto(foodPostRepository.findAllByOriginCountry(country));
    }

    @Override
    public List<FoodPostResponseDto> findAllByVeganFoodTrue() {
        return foodMapper.toFoodPostResponseDto(foodPostRepository.findAllByVeganFoodTrue());

    }

    @Override
    public List<FoodPostResponseDto> findAllByVeganFoodFalse() {
        return foodMapper.toFoodPostResponseDto(foodPostRepository.findAllByVeganFoodFalse());

    }

    @Override
    public List<FoodPostResponseDto> findAllByTimeRequiredEquals(int hour) {
        return foodMapper.toFoodPostResponseDto(foodPostRepository.findAllByTimeRequiredEquals(hour));

    }

    @Override
    public List<FoodPostResponseDto> findAllByTimeRequiredBetween(int startHour, int endHour) {
        return foodMapper.toFoodPostResponseDto(foodPostRepository.findAllByTimeRequiredBetween(startHour, endHour));

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
