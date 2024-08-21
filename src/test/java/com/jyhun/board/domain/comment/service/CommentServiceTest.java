package com.jyhun.board.domain.comment.service;

import com.jyhun.board.domain.comment.dto.CommentRequestDTO;
import com.jyhun.board.domain.comment.dto.CommentResponseDTO;
import com.jyhun.board.domain.comment.entity.Comment;
import com.jyhun.board.domain.comment.repository.CommentRepository;
import com.jyhun.board.domain.post.entity.Post;
import com.jyhun.board.domain.post.repository.PostRepository;
import com.jyhun.board.global.exception.CustomException;
import com.jyhun.board.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("댓글 목록 조회 성공")
    void findComments_Success() {
        // given
        Post post = new Post("제목", "내용", 0L, 0L);
        postRepository.save(post);
        Comment comment = new Comment("내용");
        comment.setPost(post);
        commentRepository.save(comment);

        // when
        List<CommentResponseDTO> comments = commentService.findComments(post.getId());

        // then
        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals("내용", comments.get(0).getContent());
    }

    @Test
    @DisplayName("댓글 추가 성공")
    void addComment_Success() {
        // given
        Post post = new Post("제목", "내용", 0L, 0L);
        postRepository.save(post);
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO("내용");

        // when
        CommentResponseDTO response = commentService.addComment(post.getId(), commentRequestDTO);

        // then
        assertNotNull(response);
        assertEquals("내용", response.getContent());
        assertEquals(post.getId(), response.getPostId());
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void modifyComment_Success() {
        // given
        Post post = new Post("제목", "내용", 0L, 0L);
        postRepository.save(post);
        Comment comment = new Comment("내용 1");
        comment.setPost(post);
        comment = commentRepository.save(comment);
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO("내용 2");

        // when
        CommentResponseDTO response = commentService.modifyComment(commentRequestDTO, comment.getId());

        // then
        assertNotNull(response);
        assertEquals("내용 2", response.getContent());
    }

    @Test
    @DisplayName("댓글 수정 실패: 댓글 없음")
    void modifyComment_NotFound() {
        // given
        Post post = new Post("제목", "내용", 0L, 0L);
        postRepository.save(post);
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO("내용");
        Long nonExistentCommentId = 999L;

        // when & then
        CustomException exception = assertThrows(CustomException.class, () ->
                commentService.modifyComment(commentRequestDTO, nonExistentCommentId)
        );
        assertEquals(ErrorCode.COMMENT_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void deleteComment_Success() {
        // given
        Post post = new Post("제목", "내용", 0L, 0L);
        postRepository.save(post);
        Comment comment = new Comment("내용");
        comment.setPost(post);
        comment = commentRepository.save(comment);

        // when
        commentService.deleteComment(comment.getId());

        // then
        assertFalse(commentRepository.findById(comment.getId()).isPresent());
    }

    @Test
    @DisplayName("댓글 삭제 실패: 댓글 없음")
    void deleteComment_NotFound() {
        // given
        Long nonExistentCommentId = 999L;

        // when & then
        CustomException exception = assertThrows(CustomException.class, () ->
                commentService.deleteComment(nonExistentCommentId)
        );
        assertEquals(ErrorCode.COMMENT_NOT_FOUND, exception.getErrorCode());
    }
}
