package com.faraf.repository;

import com.faraf.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodPostRepository extends PagingAndSortingRepository<User, Integer> {

    List<User> findByPosts_nameIgnoreCase(final String name);

    List<User> findByPosts_originCountryIgnoreCase(final String country);

    List<User> findByPosts_timeRequired(final int timeRequired);

}
