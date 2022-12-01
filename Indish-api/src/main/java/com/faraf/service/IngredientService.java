package com.faraf.service;

import com.faraf.dto.request.DeleteIngredientRequestDto;
import com.faraf.dto.request.IngredientsRequestDto;
import com.faraf.dto.response.IngredientResponseDto;

import java.util.List;

public interface IngredientService {
    IngredientResponseDto addIngredientToFood(IngredientsRequestDto ingredientsRequestDto, Long foodId);

    List<IngredientResponseDto> getIngredientsByFoodId(Long foodId);

    List<IngredientResponseDto> getIngredientsByFoodName(String foodName, int pageNo, int pageSize, String sort);

    List<IngredientResponseDto> getIngredientsByFoodId(Long foodId, int pageNo, int pageSize, String sort);

    void deleteByIngredientId(DeleteIngredientRequestDto requestDto);

}
