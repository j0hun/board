package com.jyhun.board.domain.post.dto;

import com.jyhun.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostRequestDTO {

    private String title;
    private String content;

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .content(content)
                .likeCount(0L)
                .viewCount(0L)
                .build();
    }

}
