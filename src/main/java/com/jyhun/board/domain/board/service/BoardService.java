package com.jyhun.board.domain.board.service;

import com.jyhun.board.domain.board.dto.BoardResponseDTO;
import com.jyhun.board.domain.board.entity.Board;
import com.jyhun.board.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

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

}
