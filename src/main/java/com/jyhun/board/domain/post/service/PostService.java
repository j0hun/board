package com.jyhun.board.domain.post.service;

import com.jyhun.board.domain.post.dto.PostRequestDTO;
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
                    log.error("ID가 {}인 게시글을 찾을 수 없습니다.", postId);
                    return new CustomException(ErrorCode.POST_NOT_FOUND);
                });

        PostResponseDTO postResponseDTO = PostResponseDTO.toDTO(post);
        log.info("ID가 {}인 게시글을 성공적으로 조회했습니다.", postId);

        return postResponseDTO;
    }

    @Transactional
    public PostResponseDTO addPost(PostRequestDTO postRequestDTO) {
        log.info("addPost 메서드 호출");

        Post post = postRequestDTO.toEntity();
        Post savedPost = postRepository.save(post);

        log.info("게시글 추가 완료, 게시글 ID: {}", savedPost.getId());
        return PostResponseDTO.toDTO(savedPost);
    }

    @Transactional
    public PostResponseDTO modifyPost(PostRequestDTO postRequestDTO, Long postId) {
        log.info("modifyPost 메서드 호출");

        Post post = postRepository.findById(postId).orElseThrow(
                () -> {
                    log.error("ID가 {}인 게시글을 찾을 수 없습니다.", postId);
                    return new CustomException(ErrorCode.POST_NOT_FOUND);
                });

        Post updatedPost = postRequestDTO.toEntity();
        post.updatePost(updatedPost);
        log.info("게시글 수정 완료, 게시글 ID: {}", postId);
        return PostResponseDTO.toDTO(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        log.info("deletePost 메서드 호출");

        Post post = postRepository.findById(postId).orElseThrow(
                () -> {
                    log.error("ID가 {}인 게시글을 찾을 수 없습니다.", postId);
                    return new CustomException(ErrorCode.POST_NOT_FOUND);
                });

        postRepository.delete(post);
        log.info("게시글 삭제 완료, 게시글 ID: {}", postId);
    }

    @Transactional
    public void increaseViewCount(Long postId) {
        log.info("increaseViewCount 메서드 호출");

        Post post = postRepository.findById(postId).orElseThrow(
                () -> {
                    log.error("ID가 {}인 게시글을 찾을 수 없습니다.", postId);
                    return new CustomException(ErrorCode.POST_NOT_FOUND);
                });
        post.increaseViewCount();
        log.info("조회수 증가 성공, 게시글 ID: {}", postId);
    }

}
