package com.faraf.service;

import com.faraf.dto.request.FoodPostRequestDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.dto.request.FoodPostUpdateRequestDto;

import java.util.List;

public interface FoodPostService {

    FoodPostResponseDto findById(long id);

    FoodPostResponseDto addFoodToUser(FoodPostRequestDto requestDto);

    List<FoodPostResponseDto> findAllByUsername(String username);

    List<FoodPostResponseDto> findAllByOriginCountry(String country);

    List<FoodPostResponseDto> findAllByVeganFoodTrue();

    List<FoodPostResponseDto> findAllByVeganFoodFalse();

    List<FoodPostResponseDto> findAllByTimeRequiredEquals(int hour);

    List<FoodPostResponseDto> findAllByTimeRequiredBetween(int startHour, int endHour);

    void updateFoodPost(FoodPostUpdateRequestDto requestDto);

}
