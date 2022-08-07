package com.faraf.service;

import com.faraf.dto.response.UserGetDto;
import com.faraf.dto.request.UserInfoUpdateRequestDto;
import com.faraf.dto.request.UserPostDto;
import com.faraf.entity.User;
import com.faraf.exception.DuplicatedRecordException;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.UserMapper;
import com.faraf.repository.UserRepository;
import com.faraf.utility.GeneralMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final GeneralMessages generalMessages;

    @Override
    @Transactional
    public UserGetDto save(UserPostDto userPostDto) {
        if (validateUser(userPostDto)) {
            User user = userMapper.toEntity(userPostDto);
            user.setPassword(passwordEncoder.encode(userPostDto.getPassword()));
            userRepository.save(user);
            return userMapper.toUserGet(user);
        } else return null;
    }

    @Override
    @Transactional
    public void updateUserInfo(UserInfoUpdateRequestDto user, Long id) {
        UserGetDto userById = getUserById(id);
        userById.setBio(user.getBio());
        userById.setCity(user.getCity());
        userById.setCountry(user.getCountry());
        userById.setAvatar(user.getAvatar());
        User user1 = userMapper.toEntity(userById);
        userRepository.save(user1);
    }

    @Override
    public List<UserGetDto> getAllUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> users = userRepository.findAll(pageable);
        PageImpl<UserGetDto> responseDtoPage = new PageImpl<>(users.getContent()
                .stream()
                .map(user -> new UserGetDto(user.getId(), user.getUserName(),
                        user.getEmail(), user.getCountry(),
                        user.getCity(), user.getPassword(),
                        user.getBio(), user.getAvatar(),
                        user.getCreate_date(), user.getModified_date()))
                .collect(Collectors.toList())
        );
        return responseDtoPage.getContent();
    }

    @Override
    public UserGetDto getUserById(Long id) {
        Optional<User> userById = userRepository.findUserById(id);
        User user = userById.orElseThrow(() -> new NotFoundException(generalMessages.getMsgUserNotFoundWithId() + id));
        return userMapper.toUserGet(user);
    }

    @Override
    public UserGetDto getUserByUsername(String username) {
        Optional<User> userByUserName = userRepository.findByUserName(username);
        User user = userByUserName.orElseThrow(() -> new NotFoundException(generalMessages.getMsgUserNotFoundWithUsername() + username));
        return userMapper.toUserGet(user);
    }

    @Override
    public UserGetDto getUserByEmail(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        User user = userByEmail.orElseThrow(() -> new NotFoundException(generalMessages.getMsgUserNotFoundWithEmail() + email));
        return userMapper.toUserGet(user);
    }

    @Override
    public String deleteUserById(Long id) {
        userRepository.deleteUserById(id);
        return "User with id: " + id + " deleted successfully!";
    }


    @Override
    @Transactional
    public String deleteUserByEmail(String email) {
        userRepository.deleteUserByEmail(email);
        return "User with email: " + email + " deleted successfully!";
    }

    @Override
    @Transactional
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return generalMessages.getMsgAllUsersDeleted();
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }


    private boolean validateUser(UserPostDto userPostDto) {
        if (userRepository.existsUserByUserName(userPostDto.getUserName())) {
            throw new DuplicatedRecordException(generalMessages.getMsgDuplicatedUsername());

        } else if (userRepository.existsUserByEmail(userPostDto.getEmail())) {
            throw new DuplicatedRecordException(generalMessages.getMsgDuplicatedEmail());

        } else
            return !userRepository.existsUserByUserName(userPostDto.getUserName()) && !userRepository.existsUserByEmail(userPostDto.getEmail());
    }

}
