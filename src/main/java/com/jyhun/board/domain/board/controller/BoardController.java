package com.jyhun.board.domain.board.controller;

import com.jyhun.board.domain.board.dto.BoardResponseDTO;
import com.jyhun.board.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getBoards() {
        log.info("모든 게시판 목록 조회 요청");

        List<BoardResponseDTO> boardList = boardService.findBoards();
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> getBoardById(@PathVariable Long boardId) {
        log.info("ID {}의 게시판 조회 요청", boardId);

        try {
            BoardResponseDTO board = boardService.findBoardById(boardId);
            log.info("ID {}의 게시판 조회 성공", boardId);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ID {}의 게시판 조회 실패: {}", boardId, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
