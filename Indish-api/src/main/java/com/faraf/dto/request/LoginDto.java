package com.faraf.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class LoginDto {

    @Email(message = "{email.valid}", regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @NotEmpty(message = "{email.blank}")
    private String email;

    @NotEmpty(message = "{password.blank}")
    private String password;

    public LoginDto() {
    }

    public LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
