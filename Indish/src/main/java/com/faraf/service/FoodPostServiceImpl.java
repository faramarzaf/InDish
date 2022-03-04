package com.faraf.service;


import com.faraf.dto.FoodPostRequestDto;
import com.faraf.dto.UserGetDto;
import com.faraf.dto.UserPostDto;
import com.faraf.entity.FoodPost;
import com.faraf.entity.User;
import com.faraf.mapper.FoodMapper;
import com.faraf.mapper.UserMapper;
import com.faraf.repository.FoodPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodPostServiceImpl implements FoodPostService {

    @Autowired
    private FoodPostRepository foodPostRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FoodMapper foodMapper;

    public List<User> getAllByName(String name) {
        return foodPostRepository.findByPosts_nameIgnoreCase(name);
    }

    public List<User> getAllByCountry(String country) {
        return foodPostRepository.findByPosts_originCountryIgnoreCase(country);
    }

    public List<User> getAllByTimeRequired(int timeRequired) {
        return foodPostRepository.findByPosts_timeRequired(timeRequired);
    }

    @Override
    public void addFoodPost(Long userId, List<FoodPostRequestDto> requestDtoList) {
        userService.addFoodToUser(userId, requestDtoList);
    }

    @Override
    public void updateFoodPost(Long userId, Long foodPostId) {

    }
}
