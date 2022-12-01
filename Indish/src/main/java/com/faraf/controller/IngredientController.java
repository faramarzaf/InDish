package com.faraf.controller;

import com.faraf.dto.request.DeleteIngredientRequestDto;
import com.faraf.dto.request.IngredientsRequestDto;
import com.faraf.dto.response.IngredientResponseDto;
import com.faraf.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/save")
    @PreAuthorize("#requestDto.userId == authentication.principal.id")
    public IngredientResponseDto save(@RequestBody IngredientsRequestDto requestDto, @RequestParam Long foodId) {
        return ingredientService.addIngredientToFood(requestDto, foodId);
    }

    @GetMapping("/by-food-name")
    public List<IngredientResponseDto> getIngredientsByFoodName(@RequestParam String foodName,
                                                                @RequestParam int pageNo,
                                                                @RequestParam int pageSize,
                                                                @RequestParam String sort) {
        return ingredientService.getIngredientsByFoodName(foodName, pageNo, pageSize, sort);
    }


    @GetMapping("/by-food-id")
    public List<IngredientResponseDto> getIngredientsByFoodId(@RequestParam Long foodId,
                                                              @RequestParam int pageNo,
                                                              @RequestParam int pageSize,
                                                              @RequestParam String sort) {
        return ingredientService.getIngredientsByFoodId(foodId, pageNo, pageSize, sort);
    }


    @DeleteMapping("/by-ingredient-id")
    @PreAuthorize("#requestDto.userId == authentication.principal.id")
    public void deleteByIngredientId(DeleteIngredientRequestDto requestDto) {
        ingredientService.deleteByIngredientId(requestDto);
    }


}
