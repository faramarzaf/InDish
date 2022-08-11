package com.faraf.controller;

import com.faraf.dto.IngredientsRequestDto;
import com.faraf.dto.IngredientsResponseDto;
import com.faraf.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/save")
    public void save(@RequestBody IngredientsRequestDto requestDto, @RequestParam Long foodId) {
        ingredientService.addIngredientToFood(requestDto, foodId);
    }

    @GetMapping("/by-food-name")
    public List<IngredientsResponseDto> getIngredientsByFoodName(@RequestParam String foodName) {
        return ingredientService.getIngredientsByFoodName(foodName);
    }


    @GetMapping("/by-food-id")
    public List<IngredientsResponseDto> getIngredientsByFoodName(@RequestParam Long foodId) {
        return ingredientService.getIngredientsByFoodId(foodId);
    }
}
