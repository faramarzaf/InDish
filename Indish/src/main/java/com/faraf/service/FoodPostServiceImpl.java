package com.faraf.service;


import com.faraf.entity.User;
import com.faraf.repository.FoodPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodPostServiceImpl {

    @Autowired
    private FoodPostRepository repository;

    public List<User> getAllByName(String name) {
        return repository.findByPosts_nameIgnoreCase(name);
    }

    public List<User> getAllByCountry(String country) {
        return repository.findByPosts_originCountryIgnoreCase(country);
    }

    public List<User> getAllByTimeRequired(int timeRequired) {
        return repository.findByPosts_timeRequired(timeRequired);
    }

}
