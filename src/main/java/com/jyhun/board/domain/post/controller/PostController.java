package com.jyhun.board.domain.post.controller;

import com.jyhun.board.domain.post.dto.PostRequestDTO;
import com.jyhun.board.domain.post.dto.PostResponseDTO;
import com.jyhun.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<PostResponseDTO> postPost(@RequestBody PostRequestDTO postRequestDTO) {
        log.info("게시글 생성 요청, 생성 데이터: {}", postRequestDTO);
        PostResponseDTO postResponseDTO = postService.addPost(postRequestDTO);
        return new ResponseEntity<>(postResponseDTO,HttpStatus.CREATED);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> patchPost(@RequestBody PostRequestDTO postRequestDTO,
                                                     @PathVariable Long postId) {
        log.info("게시글 수정 요청, 요청 데이터: {}", postRequestDTO);
        PostResponseDTO postResponseDTO = postService.modifyPost(postRequestDTO, postId);
        return new ResponseEntity<>(postResponseDTO,HttpStatus.OK);
    }

}
