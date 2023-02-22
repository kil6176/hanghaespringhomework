package com.sparta.hanghaespringhomework1.repository;


import com.sparta.hanghaespringhomework1.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikes, Long> {

    Optional<CommentLikes> findByUserIdAndCommentId(Long userId, Long commentId);

    void deleteByUserIdAndCommentId(Long id, Long commentId);
    Long countByCommentId(Long commentId);


}