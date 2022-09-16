package com.faraf.controller;

import com.faraf.entity.ConfirmationToken;
import com.faraf.exception.AuthException;
import com.faraf.service.ConfirmationTokenService;
import com.faraf.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web")
public class WebController {

    private final ConfirmationTokenService confirmationTokenService;
    private final UserServiceImpl userService;

    @GetMapping("/confirm")
    public String confirmMail(@RequestParam("token") String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
        if (optionalConfirmationToken.isPresent()) {
            ConfirmationToken confirmationToken = optionalConfirmationToken.get();
            userService.confirmUser(confirmationToken);
            return "confirm";
        } else
            throw new AuthException("Wrong credentials for email verification!");
    }
}
