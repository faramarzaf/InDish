package com.faraf.service;

import com.faraf.dto.UserGetDto;
import com.faraf.dto.UserPostDto;
import com.faraf.entity.User;
import com.faraf.exception.DuplicatedRecordException;
import com.faraf.exception.InternalServerException;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.UserMapper;
import com.faraf.repository.UserRepository;
import com.faraf.utility.GeneralMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final GeneralMessages generalMessages;

    @Override
    @Transactional
    public HashMap<String, String> save(UserPostDto user) {
        HashMap<String, String> response = new HashMap<>();
        return validateUser(response, user);
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
    public UserGetDto update(UserPostDto user, Long id) { //todo has bug
        UserGetDto userById = getUserById(id);
        userById.setUserName(user.getUserName());
        userById.setPosts(user.getPosts());
        User userToSave = repository.save(userMapper.toEntity(userById));
        return userMapper.toUserGet(userToSave);
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
        return userMapper.toUserGet(user);
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


    private HashMap<String, String> validateUser(HashMap<String, String> response, UserPostDto userPostDto) {

        if (repository.existsUserByUserName(userPostDto.getUserName())) {
            throw new DuplicatedRecordException(generalMessages.getMsgDuplicatedUsername());

        } else if (repository.existsUserByEmail(userPostDto.getEmail())) {
            throw new DuplicatedRecordException(generalMessages.getMsgDuplicatedEmail());

        } else if (!repository.existsUserByUserName(userPostDto.getUserName()) || !repository.existsUserByEmail(userPostDto.getEmail())) {
            saveUser(userPostDto);
            setSuccessfulResponseMessages(response, userPostDto, HttpStatus.OK);
        }
        return response;
    }

    private void saveUser(UserPostDto userPostDto) {
        User user = userMapper.toEntity(userPostDto);
        user.setPassword(passwordEncoder.encode(userPostDto.getPassword()));
        repository.save(user);
    }

    private void setSuccessfulResponseMessages(HashMap<String, String> response, UserPostDto userPostDto, HttpStatus httpStatus) {
        response.put("status", httpStatus.toString());
        response.put("message", userPostDto.toString() + " saved successfully!");
        response.put("created_at", LocalDateTime.now().toString());
    }

}
