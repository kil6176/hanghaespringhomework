package com.sparta.hanghaespringhomework1.dto;

import com.sparta.hanghaespringhomework1.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String username;
    private String comment;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.modifiedAt = comment.getModifiedAt();
        this.createdAt = comment.getCreatedAt();
    }
}