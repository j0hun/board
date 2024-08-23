package com.jyhun.board.global.config;

import com.jyhun.board.domain.board.dto.BoardResponseDTO;
import com.jyhun.board.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalControllerAdvice {

    private final BoardService boardService;

    @ModelAttribute
    public void addBoardsToModel(Model model) {
        List<BoardResponseDTO> boards = boardService.findBoards();
        model.addAttribute("boards", boards);
    }

}