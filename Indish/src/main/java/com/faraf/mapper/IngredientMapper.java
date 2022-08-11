package com.faraf.mapper;

import com.faraf.dto.IngredientsRequestDto;
import com.faraf.dto.IngredientsResponseDto;
import com.faraf.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {


    //  @Mapping(source = "content", target = "ingredientsRequestDto.content")
    Ingredient toEntity(IngredientsRequestDto ingredientsRequestDto);

    IngredientsResponseDto toIngredientsResponseDto(Ingredient ingredient);

    List<IngredientsResponseDto> toIngredientsResponseDto(List<Ingredient> ingredients);

}
