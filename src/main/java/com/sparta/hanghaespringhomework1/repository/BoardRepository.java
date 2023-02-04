package com.sparta.hanghaespringhomework1.repository;


import com.sparta.hanghaespringhomework1.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
}