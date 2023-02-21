package com.sparta.hanghaespringhomework1.service;


import com.sparta.hanghaespringhomework1.dto.CommentRequestDto;
import com.sparta.hanghaespringhomework1.dto.CommentResponseDto;
import com.sparta.hanghaespringhomework1.entity.Board;
import com.sparta.hanghaespringhomework1.entity.Comment;
import com.sparta.hanghaespringhomework1.entity.User;
import com.sparta.hanghaespringhomework1.entity.UserRoleEnum;
import com.sparta.hanghaespringhomework1.repository.BoardRepository;
import com.sparta.hanghaespringhomework1.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user, Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Comment comment = commentRepository.saveAndFlush(new Comment(requestDto, user, board));

        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentList(Long boardId) {
        List<Comment> commentList = commentRepository.findAllByBoardId(boardId);
        List<CommentResponseDto> commentResponseDto = new ArrayList<>();
        if (!commentList.isEmpty()) {
            for (Comment comment : commentList) {
                commentResponseDto.add(new CommentResponseDto(comment));
            }
        }
        return commentResponseDto;
    }

    @Transactional
    public CommentResponseDto update(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        if (validCheck(comment.getUser().getId(), user.getId(), user.getRole())) {
            comment.update(requestDto);
        }
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        if (validCheck(comment.getUser().getId(), user.getId(), user.getRole())) {
            commentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }

    public boolean validCheck(Long dbUsername, Long jwtUsername, UserRoleEnum admin) {
        return dbUsername.equals(jwtUsername) || admin == UserRoleEnum.ADMIN;
    }

}




