package com.jyhun.board.domain.post.controller;

import com.jyhun.board.domain.post.dto.PostRequestDTO;
import com.jyhun.board.domain.post.dto.PostResponseDTO;
import com.jyhun.board.domain.post.dto.PostSearchDTO;
import com.jyhun.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/boards/{boardId}/posts")
    public ResponseEntity<Page<PostResponseDTO>> getPosts(@PathVariable Long boardId,
                                                          @RequestParam(required = false) String searchKey,
                                                          @RequestParam(required = false) String searchValue,
                                                          @PageableDefault Pageable pageable) {
        PostSearchDTO postSearchDTO = new PostSearchDTO(searchKey, searchValue);
        log.info("게시글 목록 페이징 조회, 검색 조건: {}, 페이지: {}", postSearchDTO, pageable);
        Page<PostResponseDTO> posts = postService.findPosts(boardId,postSearchDTO, pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long postId) {
        log.info("ID {}의 게시글 조회 요청", postId);
        PostResponseDTO post = postService.findPostById(postId);
        log.info("ID {}의 게시글 조회 성공", postId);
        postService.increaseViewCount(postId);
        log.info("ID {}의 조회수 증가 성공", postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/boards/{boardId}/posts")
    public ResponseEntity<PostResponseDTO> postPost(@PathVariable Long boardId,
                                                    @RequestBody PostRequestDTO postRequestDTO) {
        log.info("게시글 생성 요청, 생성 데이터: {}", postRequestDTO);
        PostResponseDTO postResponseDTO = postService.addPost(boardId,postRequestDTO);
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
