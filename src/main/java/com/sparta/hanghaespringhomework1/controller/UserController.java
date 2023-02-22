package com.sparta.hanghaespringhomework1.controller;

import com.sparta.hanghaespringhomework1.dto.LoginRequestDto;
import com.sparta.hanghaespringhomework1.dto.Message;
import com.sparta.hanghaespringhomework1.dto.SignupRequestDto;
import com.sparta.hanghaespringhomework1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.charset.Charset;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);

        Message message = new Message(HttpStatus.OK.value(), "회원가입 완료", null);
        
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        userService.login(loginRequestDto, response);

        Message message = new Message(HttpStatus.OK.value(), "로그인 완료", null);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @RequestMapping("/forbidden")
    public ResponseEntity<Message> getForbidden() {
        Message message = new Message(HttpStatus.FORBIDDEN.value(), "로그인을 안했거나 권한이 없습니다.", null);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(message, headers, HttpStatus.FORBIDDEN);
    }
}