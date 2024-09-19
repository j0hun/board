package com.jyhun.board.domain.post.dto;

import com.jyhun.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostListDTO {

    private Long id;
    private String title;
    private Long viewCount;
    private Long likeCount;
    private Long boardId;
    private String author;

    public static PostListDTO toDTO(Post post) {
        PostListDTO PostListDTO = new PostListDTO(
                post.getId(),
                post.getTitle(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getBoard().getId(),
                post.getMember().getName()
        );
        return PostListDTO;
    }

}
