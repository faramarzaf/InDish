package com.faraf.repository;

import com.faraf.BaseTestClass;
import com.faraf.entity.FoodPost;
import com.faraf.entity.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class IngredientRepositoryTest extends BaseTestClass {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private FoodPostRepository foodPostRepository;

    private FoodPost sampleVeganFoodPost;
    private Ingredient sampleIngredient;

    @BeforeEach
    public void setUp() {
        initSampleData();
        saveSampleData();
    }

    @Test
    public void saved_ingredient_should_be_present() {
        assertThat(ingredientRepository.findById(sampleIngredient.getId()).get()).isEqualTo(sampleIngredient);
    }

    @Test
    public void it_should_find_ingredients_by_foodPost_name() {
        List<Ingredient> allByFoodPostName = ingredientRepository.findAllByFoodPost_Name(sampleVeganFoodPost.getName());
        assertThat(allByFoodPostName).isNotNull();
        assertThat(allByFoodPostName).isNotEmpty();
    }

    @Test
    public void it_should_find_ingredients_by_foodPost_id() {
        List<Ingredient> allByFoodPostId = ingredientRepository.findAllByFoodPost_Id(sampleVeganFoodPost.getId());
        assertThat(allByFoodPostId).isNotNull();
        assertThat(allByFoodPostId).isNotEmpty();
    }

    private void saveSampleData() {
        sampleVeganFoodPost = foodPostRepository.save(sampleVeganFoodPost);
        sampleIngredient = ingredientRepository.save(sampleIngredient);
    }

    private void initSampleData() {

        sampleVeganFoodPost = getSampleVeganFoodPost();
        sampleIngredient = getSampleIngredient(sampleVeganFoodPost);

    }
}
