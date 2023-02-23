package com.sparta.hanghaespringhomework1.controller;


import com.sparta.hanghaespringhomework1.dto.CommentRequestDto;
import com.sparta.hanghaespringhomework1.entity.Message;
import com.sparta.hanghaespringhomework1.security.UserDetailsImpl;
import com.sparta.hanghaespringhomework1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment/{id}")
    public ResponseEntity<Message> createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(HttpStatus.OK.value(), "댓글 작성 완료", commentService.createComment(requestDto, userDetails.getUser(), id));

        return ResponseEntity.ok(message);
    }

    @GetMapping("/api/comment/{id}")
    public ResponseEntity<Message> getCommentList(@PathVariable Long id) {
        Message message = new Message(HttpStatus.OK.value(), "댓글 조회 완료", commentService.getCommentList(id));

        return ResponseEntity.ok(message);
    }

    @PutMapping("/api/comment/{id}")
    public ResponseEntity<Message> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(HttpStatus.OK.value(), "댓글 업데이트 완료", commentService.update(id, requestDto, userDetails.getUser()));

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<Message> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(id, userDetails.getUser());

        Message message = new Message(HttpStatus.OK.value(), "댓글 삭제 완료", null);

        return ResponseEntity.ok(message);
    }

    @PostMapping("/api/comment/{id}/like")
    public ResponseEntity<Message> likeComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(HttpStatus.OK.value(), commentService.likeComment(id, userDetails.getUser()), null);

        return ResponseEntity.ok(message);
    }


}



