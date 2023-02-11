package com.sparta.hanghaespringhomework1.controller;

import com.sparta.hanghaespringhomework1.dto.LoginRequestDto;
import com.sparta.hanghaespringhomework1.dto.SignupRequestDto;
import com.sparta.hanghaespringhomework1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity signup(@Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }
        userService.signup(signupRequestDto);
        return ResponseEntity.ok(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        userService.login(loginRequestDto, response);
        return "success";
    }

}