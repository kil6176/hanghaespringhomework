package com.sparta.hanghaespringhomework1.repository;


import com.sparta.hanghaespringhomework1.entity.BoardLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLikes, Long> {
    Optional<BoardLikes> findByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByUserIdAndBoardId(Long id, Long id1);
    Long countByBoardId(Long boardId);
}