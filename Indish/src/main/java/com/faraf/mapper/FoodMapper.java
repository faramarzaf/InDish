package com.faraf.mapper;

import com.faraf.dto.request.FoodPostRequestDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.entity.FoodPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    List<FoodPost> toEntity(List<FoodPostRequestDto> foodPostRequestDtos);

    FoodPost toEntity (FoodPostResponseDto foodPostResponseDto);

    @Mapping(source = "foodPostRequestDto.userId", target = "user.id")
    FoodPost toEntity(FoodPostRequestDto foodPostRequestDto);

    List<FoodPostResponseDto> toFoodPostResponseDto(List<FoodPost> foodPostList);

    FoodPostResponseDto toFoodPostResponseDto(FoodPost foodPost);

    FoodPostRequestDto toFoodPostRequestDto(FoodPost foodPost);

    List<FoodPostRequestDto> toFoodPostRequestDto(List<FoodPostResponseDto> foodPosts);

    List<FoodPostResponseDto> toFoodResponseRequestDto(List<FoodPostRequestDto> foodPosts);


}
