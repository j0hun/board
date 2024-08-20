package com.jyhun.board.domain.post.dto;

import com.jyhun.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostResponseDTO {

    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private Long likeCount;
    private Long boardId;

    public static PostResponseDTO toDTO(Post post) {
        PostResponseDTO postResponseDTO = new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getBoard().getId()
        );
        return postResponseDTO;
    }

}
