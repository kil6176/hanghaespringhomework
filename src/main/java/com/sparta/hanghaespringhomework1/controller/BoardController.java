package com.sparta.hanghaespringhomework1.controller;


import com.sparta.hanghaespringhomework1.dto.BoardCommentDto;
import com.sparta.hanghaespringhomework1.dto.BoardRequestDto;
import com.sparta.hanghaespringhomework1.dto.BoardResponseDto;
import com.sparta.hanghaespringhomework1.entity.Message;
import com.sparta.hanghaespringhomework1.security.UserDetailsImpl;
import com.sparta.hanghaespringhomework1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/board")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    @GetMapping("/api/board")
    public JSONObject getBoardList() {
        return boardService.getBoardList();
    }

    @GetMapping("/api/board/{id}")
    public BoardCommentDto getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/api/board/{id}")
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.update(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseEntity<Message> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(id, userDetails.getUser());
        Message message = new Message(HttpStatus.OK.value(), "게시물 삭제 완료", null);


        return ResponseEntity.ok(message);
    }

    @PostMapping("/api/board/{id}/like")
    public ResponseEntity<Message> likeBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(HttpStatus.OK.value(), boardService.likeBoard(id, userDetails.getUser()), null);
        return ResponseEntity.ok(message);
    }
}



