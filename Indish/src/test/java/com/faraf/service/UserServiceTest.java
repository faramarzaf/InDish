package com.faraf.service;

import com.faraf.BaseTestClass;
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest extends BaseTestClass {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Spy
    private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailSenderService emailSenderService;

    @Mock
    private FoodPostService foodPostService;

    @Mock
    private CommentService commentService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    private User sampleUser;

    @BeforeEach
    public void setup() {
        userService = new UserServiceImpl(userRepository,
                roleRepository,
                foodPostService,
                commentService,
                confirmationTokenService,
                emailSenderService,
                userMapper,
                roleMapper,
                passwordEncoder,
                authenticationManager,
                tokenProvider
        );
        sampleUser = getCorrectSampleUser();
    }

    @Test
    public void it_should_save_user() {
        Role role = new Role();
        role.setName("USER");
        sampleUser.setRoles(Collections.singleton(role));
        UserPostDto userPostDto = userMapper.toUserPost(sampleUser);
        when(passwordEncoder.encode(anyString())).thenReturn(userPostDto.getUserPassword());
        userService.register(userPostDto);
        verify(userRepository, times(1)).save(sampleUser);
    }


    @Test
    public void save_user_should_throw_exception_with_empty_username() {
        sampleUser.setUserName(null);
        UserPostDto userPostDto = userMapper.toUserPost(sampleUser);
        assertThatThrownBy(() -> userService.register(userPostDto))
                .isInstanceOf(InternalServerException.class)
                .hasMessage("The username can not be empty!");
    }


    @Test
    public void save_user_should_throw_exception_with_empty_email() {
        sampleUser.setEmail(null);
        UserPostDto userPostDto = userMapper.toUserPost(sampleUser);
        assertThatThrownBy(() -> userService.register(userPostDto))
                .isInstanceOf(InternalServerException.class)
                .hasMessage("The email can not be empty!");
    }

    @Test
    public void save_user_should_throw_exception_with_empty_password() {
        sampleUser.setUserPassword(null);
        UserPostDto userPostDto = userMapper.toUserPost(sampleUser);
        assertThatThrownBy(() -> userService.register(userPostDto))
                .isInstanceOf(InternalServerException.class)
                .hasMessage("The password can not be empty!");
    }

    @Test
    public void save_user_should_throw_exception_with_duplicated_username() {
        Role role = new Role();
        role.setName("USER");
        sampleUser.setRoles(Collections.singleton(role));
        UserPostDto userPostDto = userMapper.toUserPost(sampleUser);
        when(userRepository.existsUserByUserName(anyString())).thenReturn(true);
        assertThatThrownBy(() -> userService.register(userPostDto))
                .isInstanceOf(DuplicatedRecordException.class)
                .hasMessage("Username has already taken, try with another one.");
    }


    @Test
    public void save_user_should_throw_exception_with_duplicated_email() {
        Role role = new Role();
        role.setName("USER");
        sampleUser.setRoles(Collections.singleton(role));
        UserPostDto userPostDto = userMapper.toUserPost(sampleUser);
        when(userRepository.existsUserByEmail(anyString())).thenReturn(true);
        assertThatThrownBy(() -> userService.register(userPostDto))
                .isInstanceOf(DuplicatedRecordException.class)
                .hasMessage("Email has already taken, try with another one.");
    }


    @Test
    public void save_user_should_throw_exception_with_invalid_roleName() {
        Role role = new Role();
        role.setName("NOT_USER");
        sampleUser.setRoles(Collections.singleton(role));
        UserPostDto userPostDto = userMapper.toUserPost(sampleUser);
        assertThatThrownBy(() -> userService.register(userPostDto))
                .isInstanceOf(InvalidRoleTypeException.class)
                .hasMessage("Invalid role type detected!");
    }


    @Test
    public void get_user_by_id_should_return_record() {
        when(userRepository.findUserById(anyLong())).thenReturn(Optional.of(sampleUser));
        UserGetDto userGetDto = userService.getUserById(anyLong());
        User user = userMapper.toEntity(userGetDto);
        assertEquals(sampleUser, user);
        verify(userRepository, times(1)).findUserById(anyLong());
    }

    @Test
    public void get_user_by_id_should_throw_exception_when_record_not_exists() {
        when(userRepository.findUserById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUserById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The user not found with id:" + 1L);
    }

}
