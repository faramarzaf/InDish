package com.faraf.web;


import com.faraf.RoleType;
import com.faraf.dto.RoleDto;
import com.faraf.dto.request.LoginDto;
import com.faraf.dto.request.UserPostDto;
import com.faraf.dto.response.UserGetDto;
import com.faraf.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    // todo add mail sender java for completing registration

    private final UserService userService;

    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        LoginDto user = new LoginDto();
        model.addAttribute("loginDto", user);
        return "login";
    }


    @RequestMapping("/login/submit")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto,
                        BindingResult result,
                        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("loginDto", loginDto);
            return "/login";
        }

        userService.loginUser(loginDto);

        return "redirect:/login?success";
    }

    @RequestMapping
    public String showHomePage() {
        return "home";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserPostDto user = new UserPostDto();
        model.addAttribute("userPostDto", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("userPostDto") UserPostDto userPostDto,
                               BindingResult result,
                               Model model) {

        if (userService.existsUserByEmail(userPostDto.getEmail()))
            result.rejectValue("email", null, "There is already an account registered with the same email");

        else if (userService.existsUserByUsername(userPostDto.getUserName()))
            result.rejectValue("userName", null, "Username has already taken, try with another one.");

        if (result.hasErrors()) {
            model.addAttribute("userPostDto", userPostDto);
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

}
