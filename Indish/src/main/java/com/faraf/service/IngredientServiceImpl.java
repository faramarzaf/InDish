package com.faraf.service;

import com.faraf.dto.IngredientsRequestDto;
import com.faraf.dto.IngredientsResponseDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.entity.FoodPost;
import com.faraf.entity.Ingredient;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.FoodMapper;
import com.faraf.mapper.IngredientMapper;
import com.faraf.repository.IngredientRepository;
import com.faraf.utility.GeneralMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final GeneralMessages generalMessages;
    private final FoodPostService foodPostService;
    private final FoodMapper foodMapper;
    private final IngredientMapper ingredientMapper;

    @Override
    @Transactional
    public void addIngredientToFood(IngredientsRequestDto ingredientsRequestDto, Long foodId) {
        FoodPostResponseDto foodPostServiceById = foodPostService.findById(foodId);
        if (ObjectUtils.isEmpty(foodPostServiceById))
            throw new NotFoundException(generalMessages.getMsgFoodNotFoundWithId() + foodId);

        FoodPost foodPost = foodMapper.toEntity(foodPostServiceById);
        Ingredient ingredientEntity = new Ingredient();
        ingredientEntity.setFoodPost(foodPost);
        ingredientEntity.setContent(ingredientsRequestDto.getContent());
        ingredientRepository.save(ingredientEntity);
    }

    @Override
    public List<IngredientsResponseDto> getIngredientsByFoodName(String foodName) {
        List<Ingredient> ingredientsByFoodName = ingredientRepository.findAllByFoodPost_Name(foodName);
        if (ObjectUtils.isEmpty(ingredientsByFoodName))
            throw new NotFoundException(generalMessages.getMsgIngredientsNotFoundWithFoodName() + foodName);

        return ingredientMapper.toIngredientsResponseDto(ingredientsByFoodName);
    }

    @Override
    public List<IngredientsResponseDto> getIngredientsByFoodId(Long foodId) {
        List<Ingredient> ingredientsByFoodId = ingredientRepository.findAllByFoodPost_Id(foodId);
        if (ObjectUtils.isEmpty(ingredientsByFoodId))
            throw new NotFoundException(generalMessages.getMsgIngredientsNotFoundWithFoodId() + foodId);

        return ingredientMapper.toIngredientsResponseDto(ingredientsByFoodId);
    }
}
