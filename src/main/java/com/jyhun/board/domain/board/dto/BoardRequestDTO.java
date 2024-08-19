package com.jyhun.board.domain.board.dto;

import com.jyhun.board.domain.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardRequestDTO {

    private String name;
    private String description;

    public Board toEntity() {
        return Board.builder()
                .name(name)
                .description(description)
                .build();
    }

}
