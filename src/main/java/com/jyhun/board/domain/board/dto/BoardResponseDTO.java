package com.jyhun.board.domain.board.dto;

import com.jyhun.board.domain.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardResponseDTO {

    private String name;
    private String description;

    public static BoardResponseDTO toDTO(Board board) {
        BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
        boardResponseDTO.setName(board.getName());
        boardResponseDTO.setDescription(board.getDescription());
        return boardResponseDTO;
    }

}
