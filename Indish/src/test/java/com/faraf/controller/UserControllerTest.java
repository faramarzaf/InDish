package com.faraf.controller;

import com.faraf.BaseTestClass;
import com.faraf.dto.request.LoginDto;
import com.faraf.dto.request.UserInfoUpdateRequestDto;
import com.faraf.entity.User;
import com.faraf.mapper.RoleMapper;
import com.faraf.mapper.UserMapper;
import com.faraf.repository.RoleRepository;
import com.faraf.repository.UserRepository;
import com.faraf.security.JwtTokenProvider;
import com.faraf.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc*/

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class UserControllerTest extends BaseTestClass {

    //  @Autowired
    private MockMvc mvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User sampleUser;

/*    @Autowired
    private UserMapper userMapper;*/

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private WebApplicationContext context;

/*    @Autowired
    FilterChainProxy springSecurityFilterChain;*/

    @BeforeEach
    public void setup() {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        //  this.mvc = MockMvcBuilders.standaloneSetup(userController).build();

        sampleUser = getCorrectSampleUser();
    }

    @Test
    // @WithMockUser
    public void login() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail(sampleUser.getEmail());
        loginDto.setUserPassword(sampleUser.getUserPassword());

        when(userService.getUserByEmail(anyString())).thenReturn(userMapper.toUserGet(sampleUser));

        MvcResult mvcResult =
                mvc.perform(post("/api/v1/user/login")
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(loginDto))
                )
                        .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
//    @WithMockUser
    public void register() {

    }

    @Test
    @WithMockUser
    public void confirm_user() {

    }


/*
    @Test
    public void get_all_users() throws Exception {
        UserGetDto user1 = new UserGetDto(1L, "SamMJ", "0123", "Sam00", "Johns00");
        UserGetDto user2 = new UserGetDto(2L, "SamMJ", "0123", "Sam00", "Johns00");
        UserGetDto user3 = new UserGetDto(3L, "SamMJ", "0123", "Sam00", "Johns00");

        List<UserGetDto> allArrivals = new ArrayList<>();

        allArrivals.add(user1);
        allArrivals.add(user2);
        allArrivals.add(user3);

        given(controller.getAllUsers()).willReturn(allArrivals);

        mvc.perform(get("/api/v1/")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].username", is(user1.getUsername())))
                .andExpect(jsonPath("$[1].username", is(user2.getUsername())))
                .andExpect(jsonPath("$[2].username", is(user3.getUsername())));

        verify(controller, times(1)).getAllUsers();

    }

    @Test
    public void get_user_by_id() throws Exception {
        UserGetDto user1 = new UserGetDto(1L, "SamMJ", "0123", "Sam00", "Johns00");

        given(controller.getById(user1.getId())).willReturn(user1);

        mvc.perform(get("/api/v1/?id=" + user1.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())

                // Although the data type of id is Long but, the returned id in json response is Integer so we cast the user1.getId() to Integer.
                .andExpect(jsonPath("id", equalTo(Integer.valueOf(user1.getId().toString()))));

        verify(controller, times(1)).getById(user1.getId());
    }

    @Test
    public void delete_user_by_id() throws Exception {
        when(controller.deleteById(1L))
                .thenReturn("User with id " + 1 + " removed.");

        MvcResult requestResult =
                mvc.perform(delete("/api/v1/")
                        .contentType(APPLICATION_JSON)
                        .param("id", "1"))
                        .andExpect(status().isOk())
                        .andExpect(status().isOk())
                        .andReturn();

        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "User with id " + 1 + " removed.");
    }


    @Test
    public void add_user() throws Exception {
        UserPostDto user1 = new UserPostDto(1L, "SamMJ", "0123", "Sam", "Johns");

        User user = new User(1L, "SamMJ", "0123", "Sam", "Johns");

        when(controller.save(any(UserPostDto.class))).thenReturn(user);
        User userUnderTest = controller.save(user1);

        MvcResult mvcResult =
                mvc.perform(post("/api/v1/save")
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(user)))
                        .andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertThat(userUnderTest.getId()).isEqualTo(1L);
        assertThat(userUnderTest.getUsername()).isEqualTo("SamMJ");
        assertThat(userUnderTest.getPassword()).isEqualTo("0123");
        assertThat(userUnderTest.getFirst_name()).isEqualTo("Sam");
        assertThat(userUnderTest.getLast_name()).isEqualTo("Johns");
        assertEquals(200, status);


    }
*/


    @Test
    @WithMockUser
    public void update_user() throws Exception {
        UserInfoUpdateRequestDto userInfoUpdateRequestDto = new UserInfoUpdateRequestDto();
        userInfoUpdateRequestDto.setBio("new bio");
        userInfoUpdateRequestDto.setCity("new city");
        userInfoUpdateRequestDto.setCountry("new country");
        userInfoUpdateRequestDto.setAvatar("new avatar");

        MvcResult mvcResult =
                mvc.perform(put("/api/v1/user/update")
                        .contentType(APPLICATION_JSON)
                        .content(mapToJson(userInfoUpdateRequestDto))
                        .param("userId", "1")
                )
                        .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }


}
