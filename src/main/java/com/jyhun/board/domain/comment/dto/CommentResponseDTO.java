package com.jyhun.board.domain.comment.dto;

import com.jyhun.board.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDTO {

    private Long id;
    private String content;
    private Long postId;
    private String author;

    public static CommentResponseDTO toDTO(Comment comment){
        return new CommentResponseDTO(comment.getId(), comment.getContent(), comment.getPost().getId(),comment.getMember().getName());
    }

}
