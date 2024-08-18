package com.jyhun.board.domain.board.repository;

import com.jyhun.board.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
