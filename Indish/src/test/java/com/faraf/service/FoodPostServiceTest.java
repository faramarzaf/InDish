package com.faraf.service;


import com.faraf.BaseTestClass;
import com.faraf.dto.request.FoodPostRequestDto;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        long userId = 1L;
        when(foodPostRepository.findById(userId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> foodPostService.findById(userId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The food not found with id:" + userId);
    }


}
