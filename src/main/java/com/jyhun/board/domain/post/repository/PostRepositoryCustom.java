package com.jyhun.board.domain.post.repository;

import com.jyhun.board.domain.post.dto.PostSearchDTO;
import com.jyhun.board.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> findPosts(PostSearchDTO postSearchDTO, Pageable pageable);
}
