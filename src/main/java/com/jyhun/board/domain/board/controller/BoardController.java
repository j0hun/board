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

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> getBoardById(@PathVariable Long boardId) {
        log.info("getBoardById 메서드 호출, boardId: {}", boardId);

        try {
            BoardResponseDTO board = boardService.findBoardById(boardId);
            log.info("ID가 {}인 게시판을 성공적으로 조회했습니다.", boardId);
            return new ResponseEntity<>(board, HttpStatus.OK);
        } catch (Exception e) {
            log.error("ID가 {}인 게시판을 조회하는 중 오류 발생: {}", boardId, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
