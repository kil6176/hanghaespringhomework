package com.sparta.hanghaespringhomework1.service;


import com.sparta.hanghaespringhomework1.dto.BoardCommentDto;
import com.sparta.hanghaespringhomework1.dto.BoardRequestDto;
import com.sparta.hanghaespringhomework1.dto.BoardResponseDto;
import com.sparta.hanghaespringhomework1.dto.CommentResponseDto;
import com.sparta.hanghaespringhomework1.entity.Board;
import com.sparta.hanghaespringhomework1.entity.BoardLikes;
import com.sparta.hanghaespringhomework1.entity.User;
import com.sparta.hanghaespringhomework1.entity.UserRoleEnum;
import com.sparta.hanghaespringhomework1.repository.BoardRepository;
import com.sparta.hanghaespringhomework1.repository.CommentRepository;
import com.sparta.hanghaespringhomework1.repository.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final BoardLikeRepository boardLikeRepository;

    private final CommentService commentService;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Board board = boardRepository.saveAndFlush(new Board(requestDto, user));

        return new BoardResponseDto(board);
    }

    @Transactional(readOnly = true)
    public JSONObject getBoardList() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtAsc();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Board board : boardList) {
            List<BoardCommentDto> boardCommentDto = new ArrayList<>();
            List<CommentResponseDto> commentResponseDto = commentService.getCommentList(board.getId());
            Long likes = boardLikeRepository.countByBoardId(board.getId());
            boardCommentDto.add(new BoardCommentDto(board, commentResponseDto, likes));

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
        Long likes = boardLikeRepository.countByBoardId(board.getId());
        return new BoardCommentDto(board, commentResponseDto, likes);
    }

    @Transactional
    public BoardResponseDto update(Long id, BoardRequestDto requestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );

        if (validCheck(board.getId(), user.getId(), user.getRole()))
            board.update(requestDto);
        else
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");

        return new BoardResponseDto(board);
    }

    @Transactional
    public void deleteBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );

        if (validCheck(board.getId(), user.getId(), user.getRole())) {
            commentRepository.deleteByBoardId(id);
            boardRepository.deleteById(id);
        } else
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
    }

    @Transactional
    public String likeBoard(Long id, User user) {
        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );

        if (boardLikeRepository.findByUserIdAndBoardId(user.getId(), id).isEmpty()) {
            boardLikeRepository.saveAndFlush(new BoardLikes(board, user));
            return "좋아요를 하셨습니다.";
        } else {
            boardLikeRepository.deleteByUserIdAndBoardId(user.getId(), id);
            return "좋아요를 취소하셨습니다.";
        }
    }

    public boolean validCheck(Long dbUsername, Long jwtUsername, UserRoleEnum admin) {
        return dbUsername.equals(jwtUsername) || admin == UserRoleEnum.ADMIN;
    }

}




