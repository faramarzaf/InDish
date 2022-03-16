package com.faraf.service;


import com.faraf.dto.FoodPostRequestDto;
import com.faraf.dto.FoodPostResponseDto;
import com.faraf.dto.FoodPostUpdateRequestDto;
import com.faraf.entity.FoodPost;
import com.faraf.mapper.FoodMapper;
import com.faraf.repository.FoodPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodPostServiceImpl implements FoodPostService {


    private final FoodPostRepository foodPostRepository;
    private final UserService userService;
    private final FoodMapper foodMapper;

    @Override
    public void addFoodPost(FoodPostRequestDto requestDto) {
        userService.addFoodToUser(requestDto);
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
        FoodPost byId = foodPostRepository.findById(requestDto.getFoodPostId());
        byId.setName(requestDto.getName());
        byId.setDescription(requestDto.getDescription());
        byId.setOriginCountry(requestDto.getOriginCountry());
        byId.setTimeRequired(requestDto.getTimeRequired());
        byId.setVeganFood(requestDto.isVeganFood());
        foodPostRepository.save(byId);
    }
}
