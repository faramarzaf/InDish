package com.faraf.service;

import com.faraf.dto.request.DeleteIngredientRequestDto;
import com.faraf.dto.request.IngredientsRequestDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.dto.response.IngredientResponseDto;
import com.faraf.entity.FoodPost;
import com.faraf.entity.Ingredient;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.FoodMapper;
import com.faraf.mapper.IngredientMapper;
import com.faraf.repository.IngredientRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service

@Transactional(readOnly = true)
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final FoodPostService foodPostService;
    private final FoodMapper foodMapper;
    private final IngredientMapper ingredientMapper;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 @Lazy FoodPostService foodPostService,
                                 FoodMapper foodMapper,
                                 IngredientMapper ingredientMapper) {
        this.ingredientRepository = ingredientRepository;
        this.foodPostService = foodPostService;
        this.foodMapper = foodMapper;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    @Transactional
    public IngredientResponseDto addIngredientToFood(IngredientsRequestDto ingredientsRequestDto, Long foodId) {
        FoodPostResponseDto foodPostServiceById = foodPostService.findById(foodId);
        if (ObjectUtils.isEmpty(foodPostServiceById))
            throw new NotFoundException("The food not found with id:" + foodId);

        FoodPost foodPost = foodMapper.toEntity(foodPostServiceById);
        Ingredient ingredientEntity = new Ingredient();
        ingredientEntity.setFoodPost(foodPost);
        ingredientEntity.setContent(ingredientsRequestDto.getContent());
        ingredientRepository.save(ingredientEntity);
        return ingredientMapper.toIngredientsResponseDto(ingredientEntity);
    }

    @Override
    public List<IngredientResponseDto> getIngredientsByFoodName(String foodName) {
        List<Ingredient> ingredientsByFoodName = ingredientRepository.findAllByFoodPost_Name(foodName);
        if (ObjectUtils.isEmpty(ingredientsByFoodName))
            throw new NotFoundException("Not found any ingredients with food name:" + foodName);

        return ingredientMapper.toIngredientsResponseDto(ingredientsByFoodName);
    }

    @Override
    public List<IngredientResponseDto> getIngredientsByFoodId(Long foodId) {
        List<Ingredient> ingredientsByFoodId = ingredientRepository.findAllByFoodPost_Id(foodId);
        if (ObjectUtils.isEmpty(ingredientsByFoodId))
            throw new NotFoundException("Not found any ingredients with food id:" + foodId);

        return ingredientMapper.toIngredientsResponseDto(ingredientsByFoodId);
    }

    @Override
    @Transactional
    public void deleteByIngredientId(DeleteIngredientRequestDto requestDto) {
        ingredientRepository.deleteById(requestDto.getIngredientId());
    }
}
