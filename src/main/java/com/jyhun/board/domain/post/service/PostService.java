package com.jyhun.board.domain.post.service;

import com.jyhun.board.domain.post.dto.PostResponseDTO;
import com.jyhun.board.domain.post.entity.Post;
import com.jyhun.board.domain.post.repository.PostRepository;
import com.jyhun.board.global.exception.CustomException;
import com.jyhun.board.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public PostResponseDTO findPostById(Long postId) {
        log.info("findPostById 메서드 호출, postId: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> {
                    log.info("ID가 {}인 게시글을 찾을 수 없습니다.", postId);
                    return new CustomException(ErrorCode.POST_NOT_FOUND);
                });
        PostResponseDTO postResponseDTO = PostResponseDTO.toDTO(post);
        log.info("ID가 {}인 게시글을 성공적으로 조회했습니다.", postId);
        return postResponseDTO;
    }

}
