package com.faraf.repository;

import com.faraf.entity.FoodPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodPostRepository extends PagingAndSortingRepository<FoodPost, Long> {

    List<FoodPost> findAllByUser_Id(Long userId);

    Page<FoodPost> findAllByUser_Id(Long userId, Pageable pageable);

    Page<FoodPost> findByUser_UserName(String username, Pageable pageable);

    Page<FoodPost> findAllByOriginCountry(String country, Pageable pageable);

    Page<FoodPost> findAllByVeganFoodTrue(Pageable pageable);

    Page<FoodPost> findAllByVeganFoodFalse(Pageable pageable);

    Page<FoodPost> findAllByTimeRequiredEquals(int hour, Pageable pageable);

    Page<FoodPost> findAllByTimeRequiredBetween(int startHour, int endHour, Pageable pageable);

}
