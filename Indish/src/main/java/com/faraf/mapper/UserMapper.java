package com.faraf.mapper;

import com.faraf.dto.FoodPostResponseDto;
import com.faraf.dto.UserGetDto;
import com.faraf.dto.UserPostDto;
import com.faraf.entity.FoodPost;
import com.faraf.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserPostDto toUserPost(User user);

    User toEntity(UserPostDto userPostDto);

    @Mapping(source = "foodPostList.id", target = "id")
    @Mapping(source = "foodPostList.name", target = "name")
    @Mapping(source = "foodPostList.description", target = "customerName")
    @Mapping(source = "foodPostList.originCountry", target = "originCountry")
    @Mapping(source = "foodPostList.timeRequired", target = "timeRequired")
    @Mapping(source = "foodPostList.isVeganFood", target = "isVeganFood")
    @Mapping(source = "foodPostList.created_date", target = "created_date")
    @Mapping(source = "foodPostList.modified_date", target = "modified_date")
    List<FoodPostResponseDto> toFoodPostResponseDto(List<FoodPost> foodPostList);

    UserGetDto toUserGet(User user);

    User toEntity(UserGetDto userGetDto);

    UserGetDto toUserGet(UserPostDto userPostDto);

    UserPostDto toUserPost(UserGetDto userGetDto);

    List<UserPostDto> toListPostDto(List<User> userList);

    List<UserGetDto> toListGetDto(List<User> userList);

    List<User> userPostToListUser(List<UserPostDto> userPostDtoList);

    List<User> userGetToListUser(List<UserGetDto> userGetDtoList);

}
