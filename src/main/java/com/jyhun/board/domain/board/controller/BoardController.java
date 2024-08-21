package com.jyhun.board.domain.board.controller;

import com.jyhun.board.domain.board.dto.BoardRequestDTO;
import com.jyhun.board.domain.board.dto.BoardResponseDTO;
import com.jyhun.board.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<BoardResponseDTO> postBoard(@RequestBody BoardRequestDTO boardRequestDTO) {
        log.info("게시판 생성 요청, 생성 데이터: {}", boardRequestDTO);
        BoardResponseDTO boardResponseDTO = boardService.addBoard(boardRequestDTO);
        return new ResponseEntity<>(boardResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> patchBoard(@RequestBody BoardRequestDTO boardRequestDTO,
                                                       @PathVariable Long boardId) {
        log.info("게시판 수정 요청, 게시판 ID:{}, 수정 데이터: {}", boardId, boardRequestDTO);
        BoardResponseDTO boardResponseDTO = boardService.modifyBoard(boardRequestDTO, boardId);
        return new ResponseEntity<>(boardResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        log.info("게시판 삭제 요청, 게시판 ID:{}", boardId);
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
