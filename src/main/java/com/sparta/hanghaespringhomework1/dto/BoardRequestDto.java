package com.sparta.hanghaespringhomework1.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String username;
    private String contents;
    private String password;
}