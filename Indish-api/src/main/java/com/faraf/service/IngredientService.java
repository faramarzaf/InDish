package com.faraf.service;

import com.faraf.dto.IngredientsRequestDto;
import com.faraf.dto.IngredientsResponseDto;

import java.util.List;

public interface IngredientService {
    void addIngredientToFood(IngredientsRequestDto ingredientsRequestDto, Long foodId);

    List<IngredientsResponseDto> getIngredientsByFoodName(String foodName);

    List<IngredientsResponseDto> getIngredientsByFoodId(Long foodId);

}
