package com.jyhun.board.domain.board.service;

import com.jyhun.board.domain.board.dto.BoardRequestDTO;
import com.jyhun.board.domain.board.dto.BoardResponseDTO;
import com.jyhun.board.domain.board.entity.Board;
import com.jyhun.board.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDTO> findBoards() {
        log.info("findBoards() 메서드 호출");

        List<Board> boardList = boardRepository.findAll();

        List<BoardResponseDTO> boardResponseDTOList = new ArrayList<>();
        for (Board board : boardList) {
            BoardResponseDTO boardResponseDTO = BoardResponseDTO.toDTO(board);
            boardResponseDTOList.add(boardResponseDTO);
        }

        return boardResponseDTOList;
    }


    @Transactional(readOnly = true)
    public BoardResponseDTO findBoardById(Long boardId) {
        log.info("findBoardById 메서드 호출, boardId: {}", boardId);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> {
                    log.error("ID가 {}인 게시판을 찾을 수 없습니다.", boardId);
                    return new RuntimeException("게시판을 찾을 수 없습니다.");
                });

        BoardResponseDTO boardResponseDTO = BoardResponseDTO.toDTO(board);

        log.info("ID가 {}인 게시판을 성공적으로 조회했습니다.", boardId);
        return boardResponseDTO;
    }

    @Transactional
    public BoardResponseDTO addBoard(BoardRequestDTO boardRequestDTO) {
        log.info("addBoard 메서드 호출");

        Board board = boardRequestDTO.toEntity();
        Board savedBoard = boardRepository.save(board);

        log.info("게시판 추가 완료, 게시판 ID: {}", savedBoard.getId());
        return BoardResponseDTO.toDTO(savedBoard);
    }

    @Transactional
    public BoardResponseDTO modifyBoard(BoardRequestDTO boardRequestDTO, Long boardId) {
        log.info("modifyBoard 메서드 호출");

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> {
                    log.error("ID가 {}인 게시판을 찾을 수 없습니다.", boardId);
                    return new RuntimeException("게시판을 찾을 수 없습니다.");
                });
        Board updatedBoard = boardRequestDTO.toEntity();
        board.updateBoard(updatedBoard);
        log.info("게시판 수정 완료, 게시판 ID: {}", boardId);
        return BoardResponseDTO.toDTO(board);
    }

}
