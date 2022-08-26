package com.faraf.service;

import com.faraf.RoleType;
import com.faraf.dto.JWTAuthResponse;
import com.faraf.dto.LoginDto;
import com.faraf.dto.request.UserInfoUpdateRequestDto;
import com.faraf.dto.request.UserPostDto;
import com.faraf.dto.response.UserGetDto;
import com.faraf.entity.Role;
import com.faraf.entity.User;
import com.faraf.exception.DuplicatedRecordException;
import com.faraf.exception.InternalServerException;
import com.faraf.exception.InvalidRoleTypeException;
import com.faraf.exception.NotFoundException;
import com.faraf.mapper.RoleMapper;
import com.faraf.mapper.UserMapper;
import com.faraf.repository.RoleRepository;
import com.faraf.repository.UserRepository;
import com.faraf.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public JWTAuthResponse loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        JWTAuthResponse jwtAuthResponse = tokenProvider.generateToken(authentication);

        return new JWTAuthResponse(jwtAuthResponse.getAccessToken(), jwtAuthResponse.getExpiresInMillis());
    }

    @Override
    @Transactional
    public UserGetDto register(UserPostDto userPostDto) {
        if (validateUser(userPostDto)) {
            User user = userMapper.toEntity(userPostDto);
            user.setPassword(passwordEncoder.encode(userPostDto.getPassword()));
            Set<Role> roles = roleMapper.toEntity(userPostDto.getRoles());
            user.setRoles(roles);
            validateRoles(roles);
            roleRepository.saveAll(user.getRoles());
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
                        user.getBio(), user.getAvatar(), roleMapper.toDto(user.getRoles()),
                        user.getCreate_date(), user.getModified_date()))
                .collect(Collectors.toList())
        );
        return responseDtoPage.getContent();
    }

    @Override
    public UserGetDto getUserById(Long id) {
        Optional<User> userById = userRepository.findUserById(id);
        User user = userById.orElseThrow(() -> new NotFoundException("The user not found with id:" + id));
        return userMapper.toUserGet(user);
    }

    @Override
    public UserGetDto getUserByUsername(String username) {
        Optional<User> userByUserName = userRepository.findByUserName(username);
        User user = userByUserName.orElseThrow(() -> new NotFoundException("The user not found with username:" + username));
        return userMapper.toUserGet(user);
    }

    @Override
    public UserGetDto getUserByEmail(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        User user = userByEmail.orElseThrow(() -> new NotFoundException("The user not found with email:" + email));
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
        return "All users deleted from database";
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }


    private boolean validateUser(UserPostDto userPostDto) {
        String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

        if (ObjectUtils.isEmpty(userPostDto.getUserName()))
            throw new InternalServerException("The username can not be empty!");

        else if (userPostDto.getUserName().length() < 3 || userPostDto.getUserName().length() > 20)
            throw new InternalServerException("The username must be between 3 and 20 characters!");

        else if (ObjectUtils.isEmpty(userPostDto.getEmail()))
            throw new InternalServerException("The email can not be empty!");

        else if (ObjectUtils.isEmpty(userPostDto.getPassword()))
            throw new InternalServerException("The password can not be empty!");

        else if (!userPostDto.getEmail().matches(emailRegex))
            throw new InternalServerException("Provide valid email address!");

        else if (userRepository.existsUserByUserName(userPostDto.getUserName()))
            throw new DuplicatedRecordException("Username has already taken, try with another one.");

        else if (userRepository.existsUserByEmail(userPostDto.getEmail()))
            throw new DuplicatedRecordException("Email has already taken, try with another one.");

        else
            return !userRepository.existsUserByUserName(userPostDto.getUserName()) && !userRepository.existsUserByEmail(userPostDto.getEmail());
    }

    private void validateRoles(Set<Role> roles) {
        List<String> roleTypes =
                Stream.of(RoleType.USER.getValue(), RoleType.ADMIN.getValue())
                        .collect(Collectors.toList());
        if (!roles
                .stream()
                .allMatch(role -> roleTypes.stream().anyMatch(roleType -> roleType.equals(role.getName())))) {

            throw new InvalidRoleTypeException("Invalid role type detected!");
        }
    }
}
