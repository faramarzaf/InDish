package com.faraf.service;


import com.faraf.BaseTestClass;
import com.faraf.dto.request.DeleteFoodPostRequestDto;
import com.faraf.dto.request.FoodPostRequestDto;
import com.faraf.dto.request.FoodPostUpdateRequestDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.entity.FoodPost;
import com.faraf.entity.User;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.FoodMapper;
import com.faraf.repository.FoodPostRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FoodPostServiceTest extends BaseTestClass {

    private FoodPostServiceImpl foodPostService;

    @Mock
    private FoodPostRepository foodPostRepository;

    @Mock
    private CommentService commentService;

    @Mock
    private IngredientService ingredientService;

    @Spy
    private FoodMapper foodMapper = Mappers.getMapper(FoodMapper.class);

    private User sampleUser;
    private FoodPost foodPost;

    @BeforeEach
    public void setup() {
        foodPostService = new FoodPostServiceImpl(
                foodPostRepository,
                foodMapper,
                ingredientService,
                commentService
        );
        sampleUser = getCorrectSampleUser();
        foodPost = getSampleVeganFoodPost(sampleUser);
    }


    @Test
    public void it_should_add_food_post_to_user() {
        FoodPostRequestDto requestDto = foodMapper.toFoodPostRequestDto(foodPost);
        when(foodMapper.toEntity(requestDto)).thenReturn(foodPost);
        FoodPostResponseDto foodPostResponseDto = foodPostService.addFoodToUser(requestDto);
        verify(foodPostRepository, times(1)).save(foodPost);
        assertNotNull(foodPostResponseDto);
    }

    @Test
    public void it_should_find_food_post_by_id() {
        when(foodPostRepository.findById(anyLong())).thenReturn(Optional.of(foodPost));
        FoodPostResponseDto foodPostServiceById = foodPostService.findById(anyLong());
        verify(foodPostRepository, times(1)).findById(anyLong());
        assertNotNull(foodPostServiceById);
    }

    @Test
    public void it_should_throw_exception_when_notFound_food_post_by_id() {
        long foodId = 1L;
        when(foodPostRepository.findById(foodId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> foodPostService.findById(foodId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The food not found with id:" + foodId);
    }


    @Test
    public void it_should_find_list_of_foods_by_userId() {
        when(foodPostRepository.findAllByUser_Id(anyLong())).thenReturn(Collections.singletonList(foodPost));
        List<FoodPostResponseDto> allByUserId = foodPostService.findAllByUserId(anyLong());
        assertThat(allByUserId).isNotNull();
        assertThat(allByUserId).isNotEmpty();
        verify(foodPostRepository, times(1)).findAllByUser_Id(anyLong());
    }

    @Test
    public void it_should_find_list_of_foods_by_userId_paginated() {
        long userId = 1L;
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        Page<FoodPost> page = new PageImpl<>(Collections.singletonList(foodPost));
        when(foodPostRepository.findAllByUser_Id(userId, pageable)).thenReturn(page);
        List<FoodPostResponseDto> allByUserId = foodPostService.findAllByUserId(userId, 1, 10, "id");
        assertThat(allByUserId).isNotNull();
        assertThat(allByUserId).isNotEmpty();
        verify(foodPostRepository, times(1)).findAllByUser_Id(userId, pageable);
    }

    @Test
    public void it_should_find_list_of_foods_by_username() {
        String userName = "name";
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        Page<FoodPost> page = new PageImpl<>(Collections.singletonList(foodPost));
        when(foodPostRepository.findByUser_UserName(userName, pageable)).thenReturn(page);
        List<FoodPostResponseDto> allByUsername = foodPostService.findAllByUsername(userName, 1, 10, "id");
        assertThat(allByUsername).isNotNull();
        assertThat(allByUsername).isNotEmpty();
        verify(foodPostRepository, times(1)).findByUser_UserName(userName, pageable);
    }


    @Test
    public void it_should_find_list_of_foods_by_country() {
        String country = "spain";
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        Page<FoodPost> page = new PageImpl<>(Collections.singletonList(foodPost));
        when(foodPostRepository.findAllByOriginCountry(country, pageable)).thenReturn(page);
        List<FoodPostResponseDto> allByOriginCountry = foodPostService.findAllByOriginCountry(country, 1, 10, "id");
        assertThat(allByOriginCountry).isNotNull();
        assertThat(allByOriginCountry).isNotEmpty();
        verify(foodPostRepository, times(1)).findAllByOriginCountry(country, pageable);
    }


    @Test
    public void it_should_find_list_of_vegan_foods() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        Page<FoodPost> page = new PageImpl<>(Collections.singletonList(foodPost));
        when(foodPostRepository.findAllByVeganFoodTrue(pageable)).thenReturn(page);
        List<FoodPostResponseDto> allByVeganFoodTrue = foodPostService.findAllByVeganFoodTrue(1, 10, "id");
        assertThat(allByVeganFoodTrue).isNotNull();
        assertThat(allByVeganFoodTrue).isNotEmpty();
        verify(foodPostRepository, times(1)).findAllByVeganFoodTrue(pageable);
    }

    @Test
    public void it_should_find_list_of_non_vegan_foods() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        Page<FoodPost> page = new PageImpl<>(Collections.singletonList(foodPost));
        when(foodPostRepository.findAllByVeganFoodFalse(pageable)).thenReturn(page);
        List<FoodPostResponseDto> allByVeganFoodFalse = foodPostService.findAllByVeganFoodFalse(1, 10, "id");
        assertThat(allByVeganFoodFalse).isNotNull();
        assertThat(allByVeganFoodFalse).isNotEmpty();
        verify(foodPostRepository, times(1)).findAllByVeganFoodFalse(pageable);
    }


    @Test
    public void it_should_find_list_of_foods_with_given_required_time() {
        int hour = 1;
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        Page<FoodPost> page = new PageImpl<>(Collections.singletonList(foodPost));
        when(foodPostRepository.findAllByTimeRequiredEquals(hour, pageable)).thenReturn(page);
        List<FoodPostResponseDto> allByTimeRequiredEquals = foodPostService.findAllByTimeRequiredEquals(hour, 1, 10, "id");
        assertThat(allByTimeRequiredEquals).isNotNull();
        assertThat(allByTimeRequiredEquals).isNotEmpty();
        verify(foodPostRepository, times(1)).findAllByTimeRequiredEquals(hour, pageable);
    }


    @Test
    public void it_should_find_list_of_foods_with_given_start_and_end_required_time() {
        int startHour = 1;
        int endHour = 3;
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id"));
        Page<FoodPost> page = new PageImpl<>(Collections.singletonList(foodPost));
        when(foodPostRepository.findAllByTimeRequiredBetween(startHour, endHour, pageable)).thenReturn(page);
        List<FoodPostResponseDto> allByTimeRequiredBetween = foodPostService.findAllByTimeRequiredBetween(startHour, endHour, 1, 10, "id");
        assertThat(allByTimeRequiredBetween).isNotNull();
        assertThat(allByTimeRequiredBetween).isNotEmpty();
        verify(foodPostRepository, times(1)).findAllByTimeRequiredBetween(startHour, endHour, pageable);
    }


    @Test
    public void it_should_updated_food_info() {
        long userId = 1L;
        long foodId = 1L;

        FoodPostUpdateRequestDto requestDto = new FoodPostUpdateRequestDto();
        requestDto.setUserId(userId);
        requestDto.setFoodPostId(foodId);
        requestDto.setName("new name");
        requestDto.setDescription("new desc");
        requestDto.setOriginCountry("new country");
        requestDto.setTimeRequired(3);
        requestDto.setVeganFood(true);

        foodPost.setUser(sampleUser);
        foodPost.setId(foodId);
        foodPost.setName(requestDto.getName());
        foodPost.setDescription(requestDto.getDescription());
        foodPost.setOriginCountry(requestDto.getOriginCountry());
        foodPost.setTimeRequired(requestDto.getTimeRequired());
        foodPost.setVeganFood(requestDto.isVeganFood());

        when(foodPostRepository.findById(userId)).thenReturn(Optional.of(foodPost));

        FoodPostResponseDto foodPostResponseDto = foodPostService.findById(userId);
        FoodPost foodPostEntity = foodMapper.toEntity(foodPostResponseDto);
        foodPostService.updateFoodPost(requestDto);
        assertEquals("new name", foodPost.getName());
        assertEquals("new desc", foodPost.getDescription());
        assertEquals("new country", foodPost.getOriginCountry());
        assertEquals(3, foodPost.getTimeRequired());
        assertTrue(foodPost.isVeganFood());
        verify(foodPostRepository, times(1)).save(foodPostEntity);
    }


    @Test
    public void it_should_delete_food_by_id() {
        long foodId = 1L;
        long userId = 1L;
        DeleteFoodPostRequestDto requestDto = new DeleteFoodPostRequestDto();
        requestDto.setUserId(userId);
        requestDto.setFoodPostId(foodId);
        doNothing().when(foodPostRepository).deleteById(foodId);
        foodPostService.deletePostByFoodId(requestDto);
        verify(foodPostRepository, times(1)).deleteById(userId);
    }

}