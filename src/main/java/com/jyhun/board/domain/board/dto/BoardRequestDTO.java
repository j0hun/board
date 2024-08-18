package com.jyhun.board.domain.board.dto;

import com.jyhun.board.domain.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
