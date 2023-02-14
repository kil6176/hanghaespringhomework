package com.sparta.hanghaespringhomework1.controller;


import com.sparta.hanghaespringhomework1.dto.CommentRequestDto;
import com.sparta.hanghaespringhomework1.dto.CommentResponseDto;
import com.sparta.hanghaespringhomework1.entity.Message;
import com.sparta.hanghaespringhomework1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.createComment(requestDto, request, id);
    }

    @GetMapping("/api/comment/{id}")
    public List<CommentResponseDto> getCommentList(@PathVariable Long id) {
        return commentService.getCommentList(id);
    }

    @PutMapping("/api/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request) {
        return commentService.update(id, requestDto, request);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<Message> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        commentService.deleteComment(id, request);
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message(HttpStatus.OK.value(), "댓글 삭제 완료", null);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

}



