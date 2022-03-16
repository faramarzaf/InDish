package com.faraf.controller;


import com.faraf.dto.UserGetDto;
import com.faraf.dto.UserInfoUpdateRequestDto;
import com.faraf.dto.UserPostDto;
import com.faraf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

/*    @GetMapping
    public ResponseEntity<List<UserGetDto>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<UserGetDto> list = service.getAllUsers(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }*/

    @RequestMapping(params = "username", method = RequestMethod.GET)
    public UserGetDto findByUsername(@RequestParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @RequestMapping(params = "email", method = RequestMethod.GET)
    public UserGetDto findByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email);
    }


    @PostMapping("/save")
    public ResponseEntity<UserGetDto> save(@Valid @RequestBody UserPostDto userPostDto) {
        return ResponseEntity.ok(userService.save(userPostDto));
    }

    @PutMapping("/update")
    public void update(@RequestBody UserInfoUpdateRequestDto user, @RequestParam("userId") Long id) {
        userService.updateUserInfo(user, id);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        return userService.deleteAllUsers();
    }

    @DeleteMapping("/deleteId")
    public String deleteById(@RequestParam("id") Long id) {
        return userService.deleteUserById(id);
    }

    @DeleteMapping("/deleteEmail")
    public String deleteByEmail(@RequestParam("email") String email) {
        return userService.deleteUserByEmail(email);
    }

}
