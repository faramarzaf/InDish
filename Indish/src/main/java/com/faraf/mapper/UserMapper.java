package com.faraf.mapper;

import com.faraf.dto.UserGetDto;
import com.faraf.dto.UserPostDto;
import com.faraf.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserPostDto toUserPost(User user);

    User toEntity(UserPostDto userPostDto);

    UserGetDto toUserGet(User user);

    User toEntity(UserGetDto userGetDto);

    UserGetDto toUserGet(UserPostDto userPostDto);

    UserPostDto toUserPost(UserGetDto userGetDto);

    List<UserPostDto> toListPostDto(List<User> userList);

    List<UserGetDto> toListGetDto(List<User> userList);

    List<User> userPostToListUser(List<UserPostDto> userPostDtoList);

    List<User> userGetToListUser(List<UserGetDto> userGetDtoList);

}
