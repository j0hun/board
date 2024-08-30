package com.jyhun.board.domain.post.controller;

import com.jyhun.board.domain.post.dto.PostRequestDTO;
import com.jyhun.board.domain.post.dto.PostResponseDTO;
import com.jyhun.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PostApiController {

    private final PostService postService;

    @PostMapping("/boards/{boardId}/posts")
    public ResponseEntity<PostResponseDTO> postPost(@PathVariable Long boardId,
                                                    @RequestBody PostRequestDTO postRequestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("게시글 생성 요청, 생성 데이터: {}", postRequestDTO);
        PostResponseDTO postResponseDTO = postService.addPost(boardId,postRequestDTO,email);
        return new ResponseEntity<>(postResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> patchPost(@RequestBody PostRequestDTO postRequestDTO,
                                                     @PathVariable Long postId) {
        log.info("게시글 수정 요청, 요청 데이터: {}", postRequestDTO);
        PostResponseDTO postResponseDTO = postService.modifyPost(postRequestDTO, postId);
        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        log.info("게시글 삭제 요청, 게시글 ID:{}", postId);
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<Void> postIncreaseLikeCount(@PathVariable Long postId) {
        log.info("ID {}의 게시글 좋아요수 증가 요청", postId);
        postService.increaseLikeCount(postId);
        log.info("ID {}의 게시글 좋아요수 증가 성공", postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
