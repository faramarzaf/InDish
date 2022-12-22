package com.faraf.controller;

import com.faraf.entity.ConfirmationToken;
import com.faraf.service.ConfirmationTokenService;
import com.faraf.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web")
public class WebController {

    private final ConfirmationTokenService confirmationTokenService;
    private final UserServiceImpl userService;

    @GetMapping("/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
        userService.confirmUser(confirmationToken);
        return "confirm";
    }
}
