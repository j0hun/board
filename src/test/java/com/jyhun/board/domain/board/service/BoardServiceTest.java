package com.jyhun.board.domain.board.service;

import com.jyhun.board.domain.board.dto.BoardRequestDTO;
import com.jyhun.board.domain.board.dto.BoardResponseDTO;
import com.jyhun.board.domain.board.entity.Board;
import com.jyhun.board.domain.board.repository.BoardRepository;
import com.jyhun.board.global.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("게시판 목록 조회")
    void testFindBoards() {
        // given
        Board board1 = new Board("이름1", "설명1");
        Board board2 = new Board("이름2", "설명2");
        List<Board> boardList = new ArrayList<>();
        boardList.add(board1);
        boardList.add(board2);
        boardRepository.saveAllAndFlush(boardList);

        // when
        List<BoardResponseDTO> result = boardService.findBoards();

        // then
        assertEquals(2,result.size());
        assertEquals("이름1", result.get(0).getName());
        assertEquals("설명1", result.get(0).getDescription());
        assertEquals("이름2", result.get(1).getName());
        assertEquals("설명2", result.get(1).getDescription());
    }

    @Test
    @DisplayName("특정 ID 게시판 조회 성공")
    void testFindBoardById_Success() {
        // given
        Board board = new Board("이름1", "설명1");
        boardRepository.saveAndFlush(board);
        Long boardId = board.getId();

        // when
        BoardResponseDTO result = boardService.findBoardById(boardId);

        // then
        assertEquals(boardId, result.getId());
        assertEquals("이름1", result.getName());
        assertEquals("설명1",result.getDescription());
    }

    @Test
    @DisplayName("특정 ID 게시판 조회 실패")
    void testFindBoardById_Failure() {
        // given
        Long boardId = 999L;

        // when & then
        Assertions.assertThrows(CustomException.class, () -> boardService.findBoardById(boardId));
    }

    @Test
    @DisplayName("게시판 추가")
    void testAddBoard() {
        // given
        BoardRequestDTO boardRequestDTO = new BoardRequestDTO("이름1", "설명1");

        // when
        BoardResponseDTO result = boardService.addBoard(boardRequestDTO);

        // then
        Board savedBoard = boardRepository.findById(result.getId()).get();
        assertEquals(result.getName(), savedBoard.getName());
        assertEquals(result.getDescription(),savedBoard.getDescription());
    }

    @Test
    @DisplayName("게시판 수정")
    void testModifyBoard() {
        // given
        Board board = new Board("이름1", "설명1");
        boardRepository.saveAndFlush(board);
        Long boardId = board.getId();
        BoardRequestDTO boardRequestDTO = new BoardRequestDTO("이름2", "설명2");

        // when
        boardService.modifyBoard(boardRequestDTO, boardId);
        Board result = boardRepository.findById(boardId).get();

        // then
        assertEquals(boardRequestDTO.getName(),result.getName());
        assertEquals(boardRequestDTO.getDescription(),result.getDescription());
    }

    @Test
    @DisplayName("게시판 삭제")
    void testDeleteBoard() {
        // given
        Board board = new Board("이름1", "설명1");
        boardRepository.saveAndFlush(board);
        Long boardId = board.getId();

        // when
        boardService.deleteBoard(boardId);

        // then
        assertEquals(boardRepository.findById(boardId), Optional.empty());
    }

}