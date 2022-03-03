package com.faraf.service;



import com.faraf.dto.UserGetDto;
import com.faraf.dto.UserPostDto;

import java.util.HashMap;

public interface UserService {

    HashMap<String, String> save(UserPostDto user);

    UserGetDto update(UserPostDto user, Long id);

  //  List<UserGetDto> getAllUsers(Integer pageNo, Integer pageSize, String sortBy);

    UserGetDto getUserById(Long id);

    UserGetDto getUserByUsername(String username);

    UserGetDto getUserByEmail(String email);

    String deleteUserById(Long id);

    String deleteUserByEmail(String email);

    String deleteAllUsers();

    boolean existsUserByEmail(String email);


}
