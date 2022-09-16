package com.faraf.config;

import com.faraf.exception.*;
import com.faraf.utility.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private Message handleMessageNotFound(NotFoundException e, HttpServletRequest request) {
        Message message = new Message();
//        message.setTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        message.setTimestamp(String.valueOf(getTimeStamp()));
        message.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        message.setStatus(HttpStatus.NOT_FOUND.value());
        message.setMessage(e.getMessage());
        message.setPath(String.valueOf(request.getRequestURI()));

        return message;
    }


    @ExceptionHandler(DuplicatedRecordException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    private Message handleMessageDuplicated(DuplicatedRecordException e, HttpServletRequest request) {
        Message message = new Message();
        message.setTimestamp(String.valueOf(getTimeStamp()));
        message.setError(HttpStatus.CONFLICT.getReasonPhrase());
        message.setStatus(HttpStatus.CONFLICT.value());
        message.setMessage(e.getMessage());
        message.setPath(String.valueOf(request.getRequestURI()));
        return message;
    }

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    private Message handleMessageAuth(AuthException e, HttpServletRequest request) {
        Message message = new Message();
        message.setTimestamp(String.valueOf(getTimeStamp()));
        message.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        message.setStatus(HttpStatus.UNAUTHORIZED.value());
        message.setMessage(e.getMessage());
        message.setPath(String.valueOf(request.getRequestURI()));

        return message;
    }


    @ExceptionHandler(InternalServerException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    private Message handleMessageInternalError(InternalServerException e, HttpServletRequest request) {
        Message message = new Message();
        message.setTimestamp(String.valueOf(getTimeStamp()));
        message.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        message.setMessage(e.getMessage());
        message.setPath(String.valueOf(request.getRequestURI()));

        return message;
    }

    @ExceptionHandler(InvalidRoleTypeException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private Message handleMessageInvalidRoleType(InvalidRoleTypeException e, HttpServletRequest request) {
        Message message = new Message();
        message.setTimestamp(String.valueOf(getTimeStamp()));
        message.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        message.setStatus(HttpStatus.BAD_REQUEST.value());
        message.setMessage(e.getMessage());
        message.setPath(String.valueOf(request.getRequestURI()));

        return message;
    }

    private LocalDateTime getTimeStamp() {
        return LocalDateTime.now();
    }
}
