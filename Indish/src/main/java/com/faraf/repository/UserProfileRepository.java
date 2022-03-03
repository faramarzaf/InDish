package com.faraf.repository;


import com.faraf.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends PagingAndSortingRepository<User, Integer> {

    List<User> findByUserProfile_cityIgnoreCase(final String city);

    List<User> findByUserProfile_countryIgnoreCase(final String country);

    Page<User> findByUserProfile_CityIgnoreCase(String city, Pageable pageable);

    Page<User> findByUserProfile_CountryIgnoreCase(String country, Pageable pageable);

}
