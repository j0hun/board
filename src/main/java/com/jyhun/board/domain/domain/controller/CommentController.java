package com.jyhun.board.domain.domain.controller;

import com.jyhun.board.domain.domain.dto.CommentRequestDTO;
import com.jyhun.board.domain.domain.dto.CommentResponseDTO;
import com.jyhun.board.domain.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getComments() {
        log.info("댓글 목록 조회 요청");
        List<CommentResponseDTO> comments = commentService.findComments();
        log.info("댓글 목록 조회 성공");
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> postComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        log.info("댓글 생성 요청, 생성 데이터: {}", commentRequestDTO);
        CommentResponseDTO commentResponseDTO = commentService.addComment(commentRequestDTO);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
    }

}
