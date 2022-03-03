package com.faraf.dto;

import java.time.LocalDateTime;

public class ResponseDto<T> {

    private String message;
    private int status;
    private LocalDateTime created_at;

    private T response;
    

    public ResponseDto() {
    }

    public ResponseDto(String message, int status, LocalDateTime created_at) {
        this.message = message;
        this.status = status;
        this.created_at = created_at;
    }

    public static ResponseDto success(){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("success");
        responseDto.setCreated_at(LocalDateTime.now());
        responseDto.setStatus(200);
        return responseDto;
    }
    
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
