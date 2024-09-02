package com.jyhun.board.domain.comment.controller;

import com.jyhun.board.domain.comment.dto.CommentRequestDTO;
import com.jyhun.board.domain.comment.dto.CommentResponseDTO;
import com.jyhun.board.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long postId) {
        log.info("댓글 목록 조회 요청");
        List<CommentResponseDTO> comments = commentService.findComments(postId);
        log.info("댓글 목록 조회 성공");
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDTO> postComment(@PathVariable Long postId,
                                                          @RequestBody CommentRequestDTO commentRequestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("댓글 생성 요청, 생성 데이터: {}", commentRequestDTO);
        CommentResponseDTO commentResponseDTO = commentService.addComment(postId,commentRequestDTO,email);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> patchComment(@RequestBody CommentRequestDTO commentRequestDTO,
                                                           @PathVariable Long commentId) {
        log.info("댓글 수정 요청, 댓글 ID:{}, 수정 데이터: {}", commentId, commentRequestDTO);
        CommentResponseDTO commentResponseDTO = commentService.modifyComment(commentRequestDTO, commentId);
        return new ResponseEntity<>(commentResponseDTO,HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        log.info("댓글 삭제 요청, 댓글 ID:{}", commentId);
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
