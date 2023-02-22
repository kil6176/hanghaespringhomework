package com.sparta.hanghaespringhomework1.dto;

import com.sparta.hanghaespringhomework1.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardCommentDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private Long likes;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    private List<CommentResponseDto> comments;

    public BoardCommentDto(Board board, List<CommentResponseDto> comment, Long likes) {
        this.id = board.getId();
        this.username = board.getUser().getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.likes = likes;
        this.comments = comment;
        this.modifiedAt = board.getModifiedAt();
        this.createdAt = board.getCreatedAt();
    }

}
