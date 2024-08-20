package com.jyhun.board.domain.domain.repository;

import com.jyhun.board.domain.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
