package com.faraf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRoleTypeException extends RuntimeException {

    public InvalidRoleTypeException(String message) {
        super(message);
    }

    public InvalidRoleTypeException(String message, Throwable cause) {
        super(message, cause);
    }

}
