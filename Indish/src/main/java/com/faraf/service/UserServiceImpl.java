package com.faraf.service;

import com.faraf.RoleType;
import com.faraf.dto.request.*;
import com.faraf.dto.response.CommentResponseDto;
import com.faraf.dto.response.FoodPostResponseDto;
import com.faraf.dto.response.JWTAuthResponse;
import com.faraf.dto.response.UserGetDto;
import com.faraf.entity.ConfirmationToken;
import com.faraf.entity.Role;
import com.faraf.entity.User;
import com.faraf.exception.*;
import com.faraf.mapper.RoleMapper;
import com.faraf.mapper.UserMapper;
import com.faraf.repository.RoleRepository;
import com.faraf.repository.UserRepository;
import com.faraf.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.mail.SimpleMailMessage;
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
    private final FoodPostService foodPostService;
    private final CommentService commentService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public JWTAuthResponse loginUser(LoginDto loginDto) {
        UserGetDto userByEmail1 = getUserByEmail(loginDto.getEmail());
        User user = userMapper.toEntity(userByEmail1);
        if (user.isEnabled()) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getUserPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // get token form tokenProvider
            JWTAuthResponse jwtAuthResponse = tokenProvider.generateToken(authentication);

            return new JWTAuthResponse(jwtAuthResponse.getAccessToken(), jwtAuthResponse.getExpiresInMillis());
        }
        throw new AuthException("Email not confirmed!");
    }

    @Override
    @Transactional
    public UserGetDto register(UserPostDto userPostDto) {
        if (validateUser(userPostDto)) {
            User user = userMapper.toEntity(userPostDto);
            user.setUserPassword(passwordEncoder.encode(userPostDto.getUserPassword()));
            Set<Role> roles = roleMapper.toEntity(userPostDto.getRoles());
            user.setRoles(roles);
            validateRoles(roles);
            roleRepository.saveAll(user.getRoles());
            userRepository.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            sendConfirmationMail(user.getEmail(), confirmationToken.getConfirmationToken());
            return userMapper.toUserGet(user);
        } else return null;
    }

    @Transactional
    public void confirmUser(ConfirmationToken confirmationToken) {
        final User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
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
                        user.getCity(), user.getUserPassword(),
                        user.getBio(), user.getAvatar(), roleMapper.toDto(user.getRoles()),
                        user.getCreate_date(), user.getModified_date(), user.getEnabled()))
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
    @Transactional
    public String deleteUserById(Long id) {
        removeUserChildes(id);
        userRepository.deleteUserById(id);
        return "User with id: " + id + " deleted successfully!";
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

        else if (ObjectUtils.isEmpty(userPostDto.getUserPassword()))
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

    private void removeUserChildes(Long userId) {
        List<CommentResponseDto> allCommentsByUserId = commentService.findByUserId(userId);
        List<FoodPostResponseDto> allFoodsByUserId = foodPostService.findAllByUserId(userId);

        for (CommentResponseDto commentResponseDto : allCommentsByUserId) {
            DeleteCommentRequestDto deleteCommentRequestDto = new DeleteCommentRequestDto();
            deleteCommentRequestDto.setCommentId(commentResponseDto.getCommentId());
            deleteCommentRequestDto.setUserId(userId);
            commentService.deleteByCommentId(deleteCommentRequestDto);
        }

        for (FoodPostResponseDto foodPostResponseDto : allFoodsByUserId) {
            DeleteFoodPostRequestDto deleteFoodPostRequestDto = new DeleteFoodPostRequestDto();
            deleteFoodPostRequestDto.setUserId(userId);
            deleteFoodPostRequestDto.setFoodPostId(foodPostResponseDto.getId());
            foodPostService.deletePostByFoodId(deleteFoodPostRequestDto);
        }
    }

    private void sendConfirmationMail(String userMail, String token) {
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText(
                "Thank you for registering. Please click on the below link to activate your account." + "http://localhost:8080/web/confirm?token="
                        + token);
        emailSenderService.sendEmail(mailMessage);
    }
}
