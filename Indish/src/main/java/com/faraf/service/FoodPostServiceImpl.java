package com.faraf.service;


import com.faraf.dto.FoodPostRequestDto;
import com.faraf.dto.FoodPostResponseDto;
import com.faraf.entity.FoodPost;
import com.faraf.mapper.FoodMapper;
import com.faraf.mapper.UserMapper;
import com.faraf.repository.FoodPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodPostServiceImpl implements FoodPostService {

    @Autowired
    private FoodPostRepository foodPostRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FoodMapper foodMapper;

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
    public void updateFoodPost(Long foodPostId) {

    }
}
