package com.jyhun.board.domain.comment.dto;

import com.jyhun.board.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentRequestDTO {

    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }

}
