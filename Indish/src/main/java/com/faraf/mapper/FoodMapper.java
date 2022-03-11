package com.faraf.mapper;

import com.faraf.dto.FoodPostRequestDto;
import com.faraf.dto.FoodPostResponseDto;
import com.faraf.entity.FoodPost;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    List<FoodPost> toEntity(List<FoodPostRequestDto> foodPostRequestDtos);

    FoodPost toEntity(FoodPostRequestDto foodPostRequestDto);

    //List<FoodPostRequestDto> toFoodPostRequestDto( List<FoodPost> foodPosts);

    FoodPostRequestDto toFoodPostRequestDto(FoodPost foodPost);

    List<FoodPostRequestDto> toFoodPostRequestDto( List<FoodPostResponseDto> foodPosts);

    List<FoodPostResponseDto> toFoodResponseRequestDto( List<FoodPostRequestDto> foodPosts);


}
