package com.sparta.hanghaespringhomework1.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Message {

    private int status;
    private String message;
    private Object data;

    public Message() {
        this.status = HttpStatus.BAD_REQUEST.value();
        this.data = null;
        this.message = null;
    }
}