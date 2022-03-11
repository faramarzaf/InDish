package com.faraf.service;

import com.faraf.dto.*;
import com.faraf.entity.User;
import com.faraf.exception.DuplicatedRecordException;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.FoodMapper;
import com.faraf.mapper.UserMapper;
import com.faraf.repository.UserRepository;
import com.faraf.utility.GeneralMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final FoodMapper foodMapper;

    private final PasswordEncoder passwordEncoder;
    private final GeneralMessages generalMessages;

    @Override
    @Transactional
    public UserGetDto save(UserPostDto userPostDto) {
        if (validateUser(userPostDto)) {
            User user = userMapper.toEntity(userPostDto);
            user.setPassword(passwordEncoder.encode(userPostDto.getPassword()));
            repository.save(user);
            return userMapper.toUserGet(user);
        } else return null;
    }

/*    @Override
    public List<UserGetDto> getAllUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> userPage = repository.findAll(paging);
        int totalElements = (int) userPage.getTotalElements();
        PageImpl<UserGetDto> userGetDtoPage = new PageImpl<>(userPage.getContent()
                .stream()
                .map(user -> new UserGetDto(
                        user.getId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getUserProfile(),
                        user.getPosts(),
                        user.getJoinedOn()

                        )
                )
                .collect(Collectors.toList()), paging, totalElements);

        return userGetDtoPage.getContent();
    }*/

    @Override
    @Transactional
    public void updateUserInfo(UserInfoUpdateRequestDto user, Long id) {
        UserGetDto userById = getUserById(id);
        userById.setBio(user.getBio());
        userById.setCity(user.getCity());
        userById.setCountry(user.getCountry());
        userById.setAvatar(user.getAvatar());

/*        FoodPostResponseDto foodPostResponseDto = new FoodPostResponseDto();
        foodPostResponseDto.setName("");
        foodPostResponseDto.setDescription("");
        foodPostResponseDto.setOriginCountry("");
        foodPostResponseDto.setTimeRequired(2);
        foodPostResponseDto.setVeganFood(false);

        List<FoodPostResponseDto> list = new ArrayList<>();
        list.add(foodPostResponseDto);*/


        User user1 = userMapper.toEntity(userById);

        repository.save(user1);
    }

    @Override
    @Transactional
    public void addFoodToUser(Long userId, FoodPostRequestDto requestDto) {//bug audit of each child food entity updates at all in each add food call
        UserGetDto userById = getUserById(userId);

        FoodPostResponseDto foodPostResponseDto = new FoodPostResponseDto();
        foodPostResponseDto.setName(requestDto.getName());
        foodPostResponseDto.setDescription(requestDto.getDescription());
        foodPostResponseDto.setOriginCountry(requestDto.getOriginCountry());
        foodPostResponseDto.setTimeRequired(requestDto.getTimeRequired());
        foodPostResponseDto.setVeganFood(requestDto.getIsVeganFood());

        userById.getPosts().add(foodPostResponseDto);
        User user = userMapper.toEntity(userById);
        repository.save(user);
    }

    @Override
    public UserGetDto getUserById(Long id) {
        Optional<User> userById = repository.findUserById(id);
        User user = userById.orElseThrow(() -> new NotFoundException(generalMessages.getMsgUserNotFoundWithId() + id));
        return userMapper.toUserGet(user);
    }

    @Override
    public UserGetDto getUserByUsername(String username) {
        Optional<User> userByUserName = repository.findByUserName(username);
        User user = userByUserName.orElseThrow(() -> new NotFoundException(generalMessages.getMsgUserNotFoundWithUsername() + username));
        return userMapper.toUserGet(user);
    }

    @Override
    public UserGetDto getUserByEmail(String email) {
        Optional<User> userByEmail = repository.findByEmail(email);
        User user = userByEmail.orElseThrow(() -> new NotFoundException(generalMessages.getMsgUserNotFoundWithEmail() + email));
        UserGetDto userGetDto = userMapper.toUserGet(user);
        return userGetDto;
    }

    @Override
    public String deleteUserById(Long id) {
        repository.deleteUserById(id);
        return "User with id: " + id + " deleted successfully!";
    }


    @Override
    @Transactional
    public String deleteUserByEmail(String email) {
        repository.deleteUserByEmail(email);
        return "User with email: " + email + " deleted successfully!";
    }

    @Override
    @Transactional
    public String deleteAllUsers() {
        repository.deleteAll();
        return generalMessages.getMsgAllUsersDeleted();
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return repository.existsUserByEmail(email);
    }


    private boolean validateUser(UserPostDto userPostDto) {
        if (repository.existsUserByUserName(userPostDto.getUserName())) {
            throw new DuplicatedRecordException(generalMessages.getMsgDuplicatedUsername());

        } else if (repository.existsUserByEmail(userPostDto.getEmail())) {
            throw new DuplicatedRecordException(generalMessages.getMsgDuplicatedEmail());

        } else
            return !repository.existsUserByUserName(userPostDto.getUserName()) && !repository.existsUserByEmail(userPostDto.getEmail());
    }

}
