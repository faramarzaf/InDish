package com.faraf.repository;

import com.faraf.entity.Ingredient;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {

    List<Ingredient> findAllByFoodPost_Name(String foodName);

    List<Ingredient> findAllByFoodPost_Id(Long foodId);

}