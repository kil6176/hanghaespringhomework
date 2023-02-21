package com.sparta.hanghaespringhomework1.repository;


import com.sparta.hanghaespringhomework1.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByUserIdAndBoardId(Long id, Long id1);
    Long countByBoardId(Long boardId);
}