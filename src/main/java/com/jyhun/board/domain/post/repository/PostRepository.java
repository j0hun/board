package com.jyhun.board.domain.post.repository;

import com.jyhun.board.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {
}
