package com.faraf.service;

import com.faraf.dto.request.DeleteIngredientRequestDto;
import com.faraf.dto.request.IngredientsRequestDto;
import com.faraf.dto.response.IngredientResponseDto;

import java.util.List;

public interface IngredientService {
    IngredientResponseDto addIngredientToFood(IngredientsRequestDto ingredientsRequestDto, Long foodId);

    List<IngredientResponseDto> getIngredientsByFoodName(String foodName);

    List<IngredientResponseDto> getIngredientsByFoodId(Long foodId);

    void deleteByIngredientId(DeleteIngredientRequestDto requestDto);

}
