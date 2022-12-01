package com.faraf.service;

import com.faraf.dto.request.DeleteFoodPostRequestDto;
import com.faraf.dto.request.FoodPostRequestDto;
import com.faraf.dto.request.FoodPostUpdateRequestDto;
import com.faraf.dto.response.FoodPostResponseDto;

import java.util.List;

public interface FoodPostService {

    FoodPostResponseDto findById(long id);

    FoodPostResponseDto addFoodToUser(FoodPostRequestDto requestDto);

    List<FoodPostResponseDto> findAllByUserId(Long userId);

    List<FoodPostResponseDto> findAllByUsername(String username, int pageNo, int pageSize, String sort);

    List<FoodPostResponseDto> findAllByUserId(Long userId, int pageNo, int pageSize, String sort);

    List<FoodPostResponseDto> findAllByOriginCountry(String country, int pageNo, int pageSize, String sort);

    List<FoodPostResponseDto> findAllByVeganFoodTrue(int pageNo, int pageSize, String sort);

    List<FoodPostResponseDto> findAllByVeganFoodFalse(int pageNo, int pageSize, String sort);

    List<FoodPostResponseDto> findAllByTimeRequiredEquals(int hour, int pageNo, int pageSize, String sort);

    List<FoodPostResponseDto> findAllByTimeRequiredBetween(int startHour, int endHour, int pageNo, int pageSize, String sort);

    void updateFoodPost(FoodPostUpdateRequestDto requestDto);

    void deletePostByFoodId(DeleteFoodPostRequestDto requestDto);

}
