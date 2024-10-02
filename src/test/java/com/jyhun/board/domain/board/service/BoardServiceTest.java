package com.jyhun.board.domain.board.service;

import com.jyhun.board.domain.board.dto.BoardRequestDTO;
import com.jyhun.board.domain.board.dto.BoardResponseDTO;
import com.jyhun.board.domain.board.entity.Board;
import com.jyhun.board.domain.board.repository.BoardRepository;
import com.jyhun.board.global.exception.CustomException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    private Board board1;
    private Board board2;

    @BeforeEach
    void before() {
        board1 = new Board("게시판 1", "설명 1");
        board2 = new Board("게시판 2", "설명 2");
        boardRepository.save(board1);
        boardRepository.save(board2);
    }

    @AfterEach
    void after() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("게시판 목록 조회")
    void testFindBoards() {
        // when
        List<BoardResponseDTO> result = boardService.findBoards();

        // then
        assertEquals(2, result.size());
        assertEquals("게시판 1", result.get(0).getName());
        assertEquals("설명 1", result.get(0).getDescription());
        assertEquals("게시판 2", result.get(1).getName());
        assertEquals("설명 2", result.get(1).getDescription());
    }

    @Test
    @DisplayName("특정 ID 게시판 조회 성공")
    void testFindBoardById_Success() {
        // given
        Long boardId = board1.getId();

        // when
        BoardResponseDTO result = boardService.findBoardById(boardId);

        // then
        assertEquals(boardId, result.getId());
        assertEquals("게시판 1", result.getName());
        assertEquals("설명 1", result.getDescription());
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
        BoardRequestDTO boardRequestDTO = new BoardRequestDTO("게시판", "설명");

        // when
        BoardResponseDTO result = boardService.addBoard(boardRequestDTO);

        // then
        Board savedBoard = boardRepository.findById(result.getId()).get();
        assertEquals(result.getName(), savedBoard.getName());
        assertEquals(result.getDescription(), savedBoard.getDescription());
    }

    @Test
    @DisplayName("게시판 수정")
    void testModifyBoard() {
        // given
        Long boardId = board1.getId();
        BoardRequestDTO boardRequestDTO = new BoardRequestDTO("게시판 1", "설명 수정");

        // when
        boardService.modifyBoard(boardRequestDTO, boardId);
        Board result = boardRepository.findById(boardId).get();

        // then
        assertEquals(boardRequestDTO.getName(), result.getName());
        assertEquals(boardRequestDTO.getDescription(), result.getDescription());
    }

    @Test
    @DisplayName("게시판 삭제")
    void testDeleteBoard() {
        // given
        Long boardId = board1.getId();

        // when
        boardService.deleteBoard(boardId);

        // then
        assertEquals(boardRepository.findById(boardId), Optional.empty());
    }

}