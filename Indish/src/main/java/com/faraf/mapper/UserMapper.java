package com.faraf.mapper;

import com.faraf.dto.request.UserPostDto;
import com.faraf.dto.response.UserGetDto;
import com.faraf.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserPostDto toUserPost(User user);

    User toEntity(UserPostDto userPostDto);

    @Mapping(target = "roles", source = "user.roles")
    UserGetDto toUserGet(User user);

    @Mapping(target = "enabled", source = "userGetDto.enabled")
    User toEntity(UserGetDto userGetDto);

    UserGetDto toUserGet(UserPostDto userPostDto);

    UserPostDto toUserPost(UserGetDto userGetDto);

    List<UserPostDto> toListPostDto(List<User> userList);

    List<UserGetDto> toListGetDto(List<User> userList);

    List<User> userPostToListUser(List<UserPostDto> userPostDtoList);

    List<User> userGetToListUser(List<UserGetDto> userGetDtoList);

}
