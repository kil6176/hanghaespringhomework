package com.sparta.hanghaespringhomework1.repository;


import com.sparta.hanghaespringhomework1.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long id);
    void deleteByBoardId(Long id);
}