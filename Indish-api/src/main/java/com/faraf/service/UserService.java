package com.faraf.service;


import com.faraf.dto.FoodPostRequestDto;
import com.faraf.dto.UserGetDto;
import com.faraf.dto.UserInfoUpdateRequestDto;
import com.faraf.dto.UserPostDto;

import java.util.HashMap;
import java.util.List;

public interface UserService {

    HashMap<String, String> save(UserPostDto user);

    UserGetDto updateUserInfo(UserInfoUpdateRequestDto user, Long id);

    void addFoodToUser(Long userId ,FoodPostRequestDto foodPostRequestDto);

    //  List<UserGetDto> getAllUsers(Integer pageNo, Integer pageSize, String sortBy);

    UserGetDto getUserById(Long id);

    UserGetDto getUserByUsername(String username);

    UserGetDto getUserByEmail(String email);

    String deleteUserById(Long id);

    String deleteUserByEmail(String email);

    String deleteAllUsers();

    boolean existsUserByEmail(String email);


}
