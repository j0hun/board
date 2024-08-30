package com.jyhun.board.domain.post.dto;

import com.jyhun.board.domain.comment.dto.CommentResponseDTO;
import com.jyhun.board.domain.comment.entity.Comment;
import com.jyhun.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private String author;
    private List<CommentResponseDTO> commentResponseDTOList;

    public static PostResponseDTO toDTO(Post post) {
        List<Comment> commentList = post.getCommentList();
        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentResponseDTO commentResponseDTO = CommentResponseDTO.toDTO(comment);
            commentResponseDTOList.add(commentResponseDTO);
        }
        PostResponseDTO postResponseDTO = new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getLikeCount(),
                post.getBoard().getId(),
                post.getMember().getName(),
                commentResponseDTOList
        );
        return postResponseDTO;
    }

}
