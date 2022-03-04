package com.faraf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicatedRecordException extends RuntimeException{


    public DuplicatedRecordException(String message) {
        super(message);
    }

    public DuplicatedRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
