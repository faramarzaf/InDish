package com.faraf.service;


import com.faraf.dto.request.LoginDto;
import com.faraf.dto.request.UserInfoUpdateRequestDto;
import com.faraf.dto.request.UserPostDto;
import com.faraf.dto.response.JWTAuthResponse;
import com.faraf.dto.response.UserGetDto;

import java.util.List;

public interface UserService {

    UserGetDto register(UserPostDto user);

    JWTAuthResponse loginUser(LoginDto loginDto);

    void updateUserInfo(UserInfoUpdateRequestDto user, Long id);

    List<UserGetDto> getAllUsers(Integer pageNo, Integer pageSize, String sort);

    UserGetDto getUserById(Long id);

    UserGetDto getUserByUsername(String username);

    UserGetDto getUserByEmail(String email);

    String deleteUserById(Long id);

    boolean existsUserByEmail(String email);


}
