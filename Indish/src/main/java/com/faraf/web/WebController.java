package com.faraf.web;


import com.faraf.RoleType;
import com.faraf.dto.RoleDto;
import com.faraf.dto.request.LoginDto;
import com.faraf.dto.request.UserPostDto;
import com.faraf.dto.response.UserGetDto;
import com.faraf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    UserService userService;


    @RequestMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserPostDto user = new UserPostDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserPostDto userPostDto,
                                       BindingResult result,
                                       Model model) {

        if (userService.existsUserByEmail(userPostDto.getEmail()))
            result.rejectValue("email", null, "There is already an account registered with the same email");

        if (result.hasErrors()) {
            model.addAttribute("user", userPostDto);
            return "/register";
        }

        RoleDto roleDto = new RoleDto();
        roleDto.setName(RoleType.USER.getValue());
        userPostDto.setRoles(Collections.singleton(roleDto));
        //    model.addAttribute("user", userPostDto);
        userService.register(userPostDto);
        return "redirect:/register?success";
    }


    @GetMapping("/users")
    public String users(Model model) {
        List<UserGetDto> allUsers = userService.getAllUsers(0, 30, "id");
        model.addAttribute("users", allUsers);
        return "users";
    }

    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }


}
