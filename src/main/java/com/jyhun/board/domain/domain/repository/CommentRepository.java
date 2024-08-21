package com.jyhun.board.domain.domain.repository;

import com.jyhun.board.domain.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM Comment c where c.post.id = :postId")
    List<Comment> findAllByPostId(Long postId);

}
