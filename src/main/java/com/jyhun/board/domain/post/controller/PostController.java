package com.jyhun.board.domain.post.controller;

import com.jyhun.board.domain.post.dto.PostResponseDTO;
import com.jyhun.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long postId) {
        log.info("ID {}의 게시글 조회 요청", postId);
        PostResponseDTO post = postService.findPostById(postId);
        log.info("ID {}의 게시글 조회 성공", postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

}
