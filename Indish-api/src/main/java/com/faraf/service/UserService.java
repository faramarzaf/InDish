package com.faraf.service;


import com.faraf.dto.JWTAuthResponse;
import com.faraf.dto.LoginDto;
import com.faraf.dto.request.UserInfoUpdateRequestDto;
import com.faraf.dto.request.UserPostDto;
import com.faraf.dto.response.UserGetDto;

import java.util.List;

public interface UserService {

    UserGetDto register(UserPostDto user);

    JWTAuthResponse loginUser(LoginDto loginDto);

    void updateUserInfo(UserInfoUpdateRequestDto user, Long id);

    List<UserGetDto> getAllUsers(Integer pageNo, Integer pageSize, String sortBy);

    UserGetDto getUserById(Long id);

    UserGetDto getUserByUsername(String username);

    UserGetDto getUserByEmail(String email);

    String deleteUserById(Long id);

    String deleteUserByEmail(String email);

    String deleteAllUsers();

    boolean existsUserByEmail(String email);


}
