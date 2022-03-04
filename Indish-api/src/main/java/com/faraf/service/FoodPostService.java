package com.faraf.service;

import com.faraf.dto.FoodPostRequestDto;

import java.util.List;

public interface FoodPostService {


    void addFoodPost(Long userId,  FoodPostRequestDto requestDto);

    void updateFoodPost(Long userId, Long foodPostId);

}
