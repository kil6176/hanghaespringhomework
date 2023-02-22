package com.sparta.hanghaespringhomework1.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message {

    private int status;
    private String message;
    private Object data;

    public Message(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}