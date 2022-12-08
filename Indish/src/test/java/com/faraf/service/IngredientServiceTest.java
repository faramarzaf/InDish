package com.faraf.service;

import com.faraf.BaseTestClass;
import com.faraf.dto.request.DeleteIngredientRequestDto;
import com.faraf.dto.request.IngredientsRequestDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.dto.response.IngredientResponseDto;
import com.faraf.entity.FoodPost;
import com.faraf.entity.Ingredient;
import com.faraf.entity.User;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.FoodMapper;
import com.faraf.mapper.IngredientMapper;
import com.faraf.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class IngredientServiceTest extends BaseTestClass {

    private IngredientServiceImpl ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private FoodPostService foodPostService;

    @Spy
    private FoodMapper foodMapper = Mappers.getMapper(FoodMapper.class);

    @Spy
    private IngredientMapper ingredientMapper = Mappers.getMapper(IngredientMapper.class);

    private User sampleUser;
    private FoodPost foodPost;
    private Ingredient ingredient;

    @BeforeEach
    public void setup() {
        ingredientService = new IngredientServiceImpl(
                ingredientRepository,
                foodPostService,
                foodMapper,
                ingredientMapper
        );
        sampleUser = getCorrectSampleUser();
        foodPost = getSampleVeganFoodPost(sampleUser);
        ingredient = getSampleIngredient(foodPost);
    }


    @Test
    public void it_should_add_ingredient_to_foodPost() {
        IngredientsRequestDto ingredientsRequestDto = new IngredientsRequestDto();
        ingredientsRequestDto.setContent(ingredient.getContent());
        FoodPostResponseDto foodPostResponseDto = foodMapper.toFoodPostResponseDto(foodPost);
        when(foodMapper.toEntity(foodPostResponseDto)).thenReturn(foodPost);
        when(foodPostService.findById(anyLong())).thenReturn(foodPostResponseDto);
        IngredientResponseDto ingredientResponseDto = ingredientService.addIngredientToFood(ingredientsRequestDto, anyLong());
        verify(ingredientRepository, times(1)).save(ingredient);
        assertNotNull(ingredientResponseDto);
    }

    @Test
    public void it_should_throw_exception_when_add_ingredient_to_foodPost_with_given_foodId() {
        long foodId = 1L;
        when(foodPostService.findById(anyLong())).thenReturn(null);
        assertThatThrownBy(() -> ingredientService.addIngredientToFood(any(IngredientsRequestDto.class), foodId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The food not found with id:" + foodId);
    }


    @Test
    public void it_should_find_ingredients_by_id() {
        when(ingredientRepository.findAllByFoodPost_Id(anyLong())).thenReturn(Collections.singletonList(ingredient));
        List<IngredientResponseDto> ingredientsByFoodId = ingredientService.getIngredientsByFoodId(anyLong());
        assertThat(ingredientsByFoodId).isNotNull();
        assertThat(ingredientsByFoodId).isNotEmpty();
        verify(ingredientRepository, times(1)).findAllByFoodPost_Id(anyLong());
    }


    @Test
    public void it_should_find_ingredients_by_id_paginated() {
        long foodId = 1L;
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        PageImpl<Ingredient> ingredients = new PageImpl<>(Collections.singletonList(ingredient));
        when(ingredientRepository.findAllByFoodPost_Id(foodId, pageable)).thenReturn(ingredients);
        List<IngredientResponseDto> ingredientsByFoodId = ingredientService.getIngredientsByFoodId(foodId, 1, 10, "id");
        assertThat(ingredientsByFoodId).isNotNull();
        assertThat(ingredientsByFoodId).isNotEmpty();
        verify(ingredientRepository, times(1)).findAllByFoodPost_Id(foodId, pageable);
    }


    @Test
    public void it_should_throw_exception_find_ingredients_by_given_foodId() {
        long foodId = 1L;
        when(ingredientRepository.findAllByFoodPost_Id(anyLong())).thenReturn(null);
        assertThatThrownBy(() -> ingredientService.getIngredientsByFoodId(foodId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not found any ingredients with food id:" + foodId);
    }


    @Test
    public void it_should_throw_exception_find_ingredients_by_given_foodId_paginated() {
        long foodId = 1L;
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        when(ingredientRepository.findAllByFoodPost_Id(foodId, pageable)).thenReturn(Page.empty());
        assertThatThrownBy(() -> ingredientService.getIngredientsByFoodId(foodId, 1, 10, "id"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not found any ingredients with food id:" + foodId);
    }


    @Test
    public void it_should_find_ingredients_by_foodName_paginated() {
        String foodName = foodPost.getName();
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        PageImpl<Ingredient> ingredients = new PageImpl<>(Collections.singletonList(ingredient));
        when(ingredientRepository.findAllByFoodPost_Name(foodName, pageable)).thenReturn(ingredients);
        List<IngredientResponseDto> ingredientsByFoodId = ingredientService.getIngredientsByFoodName(foodName, 1, 10, "id");
        assertThat(ingredientsByFoodId).isNotNull();
        assertThat(ingredientsByFoodId).isNotEmpty();
        verify(ingredientRepository, times(1)).findAllByFoodPost_Name(foodName, pageable);
    }

    @Test
    public void it_should_throw_exception_find_ingredients_by_foodName_paginated() {
        String foodName = foodPost.getName();
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        when(ingredientRepository.findAllByFoodPost_Name(foodName, pageable)).thenReturn(Page.empty());
        assertThatThrownBy(() -> ingredientService.getIngredientsByFoodName(foodName, 1, 10, "id"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not found any ingredients with food name:" + foodName);
    }

    @Test
    public void it_should_delete_ingredient_by_id() {
        long ingredientId = 1L;
        long userId = 2L;
        DeleteIngredientRequestDto requestDto = new DeleteIngredientRequestDto();
        requestDto.setUserId(userId);
        requestDto.setIngredientId(ingredientId);
        doNothing().when(ingredientRepository).deleteById(ingredientId);
        ingredientService.deleteByIngredientId(requestDto);
        verify(ingredientRepository, times(1)).deleteById(ingredientId);
    }
}
