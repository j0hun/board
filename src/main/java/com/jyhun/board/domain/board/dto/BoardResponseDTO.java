package com.jyhun.board.domain.board.dto;

import com.jyhun.board.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardResponseDTO {

    private Long id;
    private String name;
    private String description;

    public static BoardResponseDTO toDTO(Board board) {
        BoardResponseDTO boardResponseDTO = new BoardResponseDTO(board.getId(),board.getName(),board.getDescription());
        return boardResponseDTO;
    }

}
