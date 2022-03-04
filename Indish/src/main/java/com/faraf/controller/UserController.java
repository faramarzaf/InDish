package com.faraf.controller;


import com.faraf.dto.UserGetDto;
import com.faraf.dto.UserPostDto;
import com.faraf.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserServiceImpl service;

    //http://localhost:8080/api/v1/user?pageSize=20&pageNo=0&sortBy=id    pageSize=10---> returns 10 user
/*    @GetMapping
    public ResponseEntity<List<UserGetDto>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<UserGetDto> list = service.getAllUsers(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }*/


    //localhost:8080/api/v1/user?username=Tom
    @RequestMapping(params = "username", method = RequestMethod.GET)
    public UserGetDto findByUsername(@RequestParam("username") String username) {
        return service.getUserByUsername(username);
    }

    //localhost:8080/api/v1/user?email=Tom@gmail.com
    @RequestMapping(params = "email", method = RequestMethod.GET)
    public UserGetDto findByEmail(@RequestParam("email") String email) {
        return service.getUserByEmail(email);
    }


    @PostMapping("/save")
    public HashMap<String, String> save(@Valid @RequestBody UserPostDto user) {
        return service.save(user);
    }

    //localhost:8080/api/v1/user/update?id=15
    @PutMapping("/update")
    public UserGetDto update(@RequestBody UserPostDto user, @RequestParam("id") Long id) {
        return service.update(user, id);
    }

    //localhost:8080/api/v1/user/deleteAll
    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        return service.deleteAllUsers();
    }

    //localhost:8080/api/v1/user/deleteId?id=67
    @DeleteMapping("/deleteId")
    public String deleteById(@RequestParam("id") Long id) {
        return service.deleteUserById(id);
    }

    //localhost:8080/api/v1/deleteEmail?email=address@gmail.com
    @DeleteMapping("/deleteEmail")
    public String deleteByEmail(@RequestParam("email") String email) {
        return service.deleteUserByEmail(email);
    }

}
