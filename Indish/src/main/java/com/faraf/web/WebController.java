package com.faraf.web;


import com.faraf.dto.request.LoginDto;
import com.faraf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

    @Autowired
    UserService userService;


    @RequestMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public void signIn(Model model) {
        model.addAttribute("email");
        model.addAttribute("password");
        LoginDto loginDto = new LoginDto();
        String email = String.valueOf(model.getAttribute("email"));
        String password = String.valueOf(model.getAttribute("password"));
        loginDto.setEmail(email);
        loginDto.setPassword(password);
        userService.loginUser(loginDto);
    }


    // Login form with error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }


}
