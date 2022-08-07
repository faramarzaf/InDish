package com.faraf.service;


import com.faraf.dto.request.FoodPostRequestDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.dto.request.FoodPostUpdateRequestDto;
import com.faraf.entity.FoodPost;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.FoodMapper;
import com.faraf.repository.FoodPostRepository;
import com.faraf.utility.GeneralMessages;
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
    private final GeneralMessages generalMessages;

    @Override
    public FoodPostResponseDto findById(long id) {
        Optional<FoodPost> optFoodPost = foodPostRepository.findById(id);
        if (optFoodPost.isPresent()) {
            FoodPost foodPost = optFoodPost.get();
            return foodMapper.toFoodPostResponseDto(foodPost);
        } else throw new NotFoundException(generalMessages.getMsgFoodNotFoundWithId() + id);
    }

    @Override
    @Transactional
    public void addFoodToUser(FoodPostRequestDto requestDto) {
        FoodPost foodPost = foodMapper.toEntity(requestDto);
        foodPostRepository.save(foodPost);
    }

    @Override
    public List<FoodPostResponseDto> findAllByUsername(String username) {
        List<FoodPost> allByUser_userName = foodPostRepository.findByUser_UserName(username);
        return foodMapper.toFoodPostResponseDto(allByUser_userName);
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
}
