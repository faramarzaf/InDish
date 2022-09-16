package com.faraf.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginDto {

    @Email(message = "{email.valid}", regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @NotEmpty(message = "{email.blank}")
    private String email;

    @NotEmpty(message = "{password.blank}")
    private String userPassword;

    public LoginDto() {
    }

    public LoginDto(String email, String userPassword) {
        this.email = email;
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
