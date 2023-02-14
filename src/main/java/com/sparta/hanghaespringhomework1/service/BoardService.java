package com.sparta.hanghaespringhomework1.service;


import com.sparta.hanghaespringhomework1.dto.BoardCommentDto;
import com.sparta.hanghaespringhomework1.dto.BoardRequestDto;
import com.sparta.hanghaespringhomework1.dto.BoardResponseDto;
import com.sparta.hanghaespringhomework1.dto.CommentResponseDto;
import com.sparta.hanghaespringhomework1.entity.Board;
import com.sparta.hanghaespringhomework1.entity.User;
import com.sparta.hanghaespringhomework1.entity.UserRoleEnum;
import com.sparta.hanghaespringhomework1.jwt.JwtUtil;
import com.sparta.hanghaespringhomework1.repository.BoardRepository;
import com.sparta.hanghaespringhomework1.repository.CommentRepository;
import com.sparta.hanghaespringhomework1.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시판 추가
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져옴
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUserId(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Board board = boardRepository.saveAndFlush(new Board(requestDto, user));

            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public JSONObject getBoardList() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtAsc();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Board board : boardList) {
            List<BoardCommentDto> boardCommentDto = new ArrayList<>();
            List<CommentResponseDto> commentResponseDto = commentService.getCommentList(board.getId());
            boardCommentDto.add(new BoardCommentDto(board, commentResponseDto));

            jsonArray.add(boardCommentDto);
        }

        jsonObject.put("postList", jsonArray);


        return jsonObject;
    }

    @Transactional(readOnly = true)
    public BoardCommentDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );

        List<CommentResponseDto> commentResponseDto = commentService.getCommentList(board.getId());

        return new BoardCommentDto(board, commentResponseDto);
    }

    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시판 추가
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져옴
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUserId(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            if (validCheck(board.getId(), user.getId(), user.getRole()))
                board.update(requestDto);
            else
                throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
            return new BoardResponseDto(board);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteBoard(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시판 추가
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져옴
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUserId(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            if (validCheck(board.getId(), user.getId(), user.getRole())) {
                commentRepository.deleteByBoardId(id);
                boardRepository.deleteById(id);
            }
            else
                throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    public boolean validCheck(Long dbUsername, Long jwtUsername, UserRoleEnum admin) {
        return dbUsername.equals(jwtUsername) || admin == UserRoleEnum.ADMIN;
    }

}




