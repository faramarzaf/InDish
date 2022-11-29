package com.faraf;

import com.faraf.entity.*;

import java.time.LocalDateTime;

public class BaseTestClass {

    public User getCorrectSampleUser() {
        User user = new User();
        user.setUserName("user1");
        user.setEmail("sample@gmail.com");
        user.setUserPassword("pass");
        user.setBio("sample bio");
        user.setCountry("italy");
        return user;
    }


    public FoodPost getSampleVeganFoodPost() {
        FoodPost foodPost = new FoodPost();
        foodPost.setName("food name");
        foodPost.setDescription("sample description");
        foodPost.setOriginCountry("italy");
        foodPost.setVeganFood(true);
        foodPost.setTimeRequired(1);
        return foodPost;
    }

    public FoodPost getSampleVeganFoodPost(User user) {
        FoodPost foodPost = new FoodPost();
        foodPost.setName("food name");
        foodPost.setDescription("sample description");
        foodPost.setOriginCountry("italy");
        foodPost.setVeganFood(true);
        foodPost.setTimeRequired(1);
        foodPost.setUser(user);
        return foodPost;
    }


    public FoodPost getSampleNonVeganFoodPost() {
        FoodPost foodPost = new FoodPost();
        foodPost.setName("food name");
        foodPost.setDescription("sample description");
        foodPost.setOriginCountry("italy");
        foodPost.setVeganFood(false);
        foodPost.setTimeRequired(2);
        return foodPost;
    }

    public Comment getSampleComment(User user, FoodPost foodPost) {
        Comment comment = new Comment();
        comment.setContent("This is sample comment");
        comment.setUser(user);
        comment.setPost(foodPost);
        return comment;
    }

    public Ingredient getSampleIngredient(FoodPost foodPost) {
        Ingredient ingredient = new Ingredient();
        ingredient.setContent("Sample ingredient");
        ingredient.setFoodPost(foodPost);
        return ingredient;
    }

    public ConfirmationToken getSampleConfirmationToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(user);
        confirmationToken.setConfirmationToken("token");
        confirmationToken.setCreatedDate(LocalDateTime.now());
        return confirmationToken;
    }
}
