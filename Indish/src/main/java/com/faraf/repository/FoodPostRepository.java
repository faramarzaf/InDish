package com.faraf.repository;

import com.faraf.entity.FoodPost;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodPostRepository extends PagingAndSortingRepository<FoodPost, Long> {

    List<FoodPost> findByUser_UserName(String username);

    List<FoodPost> findAllByOriginCountry(String country);

    List<FoodPost> findAllByVeganFoodTrue();

    List<FoodPost> findAllByVeganFoodFalse();

    List<FoodPost> findAllByTimeRequiredEquals(int hour);

    List<FoodPost> findAllByTimeRequiredBetween(int startHour, int endHour);


}
