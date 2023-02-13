package com.sparta.hanghaespringhomework1.repository;


import com.sparta.hanghaespringhomework1.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long id);
    Optional<Comment> findByBoardId(Long id);
    void deleteByBoardId(Long id);
}