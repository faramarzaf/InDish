package com.faraf.controller;


import com.faraf.dto.request.LoginDto;
import com.faraf.dto.request.UserInfoUpdateRequestDto;
import com.faraf.dto.request.UserPostDto;
import com.faraf.dto.response.JWTAuthResponse;
import com.faraf.dto.response.UserGetDto;
import com.faraf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<UserGetDto> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sort) {
        return userService.getAllUsers(pageNo, pageSize, sort);
    }

    @GetMapping("/by-id")
    public UserGetDto findById(@RequestParam Long id) {
        return userService.getUserById(id);
    }

    @RequestMapping(params = "username", method = RequestMethod.GET)
    public UserGetDto findByUsername(@RequestParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @RequestMapping(params = "email", method = RequestMethod.GET)
    public UserGetDto findByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/register")
    public ResponseEntity<UserGetDto> register(@Valid @RequestBody UserPostDto userPostDto) {
        return ResponseEntity.ok(userService.register(userPostDto));
    }

    @PostMapping("/login")
    public JWTAuthResponse login(@Valid @RequestBody LoginDto loginDto) {
        return userService.loginUser(loginDto);
    }

    @PutMapping("/update")
    @PreAuthorize("#id == authentication.principal.id")
    public void update(@RequestBody UserInfoUpdateRequestDto user, @RequestParam("userId") Long id) {
        userService.updateUserInfo(user, id);
    }

    @DeleteMapping("/delete-by-id")
    public String deleteById(@RequestParam("id") Long id) {
        return userService.deleteUserById(id);
    }

}
