package com.faraf.mapper;

import com.faraf.dto.request.IngredientsRequestDto;
import com.faraf.dto.response.IngredientResponseDto;
import com.faraf.entity.Ingredient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {


    //  @Mapping(source = "content", target = "ingredientsRequestDto.content")
    Ingredient toEntity(IngredientsRequestDto ingredientsRequestDto);

    IngredientResponseDto toIngredientsResponseDto(Ingredient ingredient);

    List<IngredientResponseDto> toIngredientsResponseDto(List<Ingredient> ingredients);

}
