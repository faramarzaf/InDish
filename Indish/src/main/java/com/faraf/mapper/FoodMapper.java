package com.faraf.mapper;

import com.faraf.dto.FoodPostRequestDto;
import com.faraf.dto.FoodPostResponseDto;
import com.faraf.entity.FoodPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    List<FoodPost> toEntity(List<FoodPostRequestDto> foodPostRequestDtos);

    @Mapping(source = "foodPostRequestDto.userId", target = "user.id")
    FoodPost toEntity(FoodPostRequestDto foodPostRequestDto);

    List<FoodPostResponseDto> toFoodPostResponseDto(List<FoodPost> foodPostList);

    FoodPostRequestDto toFoodPostRequestDto(FoodPost foodPost);

    List<FoodPostRequestDto> toFoodPostRequestDto(List<FoodPostResponseDto> foodPosts);

    List<FoodPostResponseDto> toFoodResponseRequestDto(List<FoodPostRequestDto> foodPosts);


}
