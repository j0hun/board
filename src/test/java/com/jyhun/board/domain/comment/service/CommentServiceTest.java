package com.jyhun.board.domain.comment.service;

import com.jyhun.board.domain.board.entity.Board;
import com.jyhun.board.domain.board.repository.BoardRepository;
import com.jyhun.board.domain.comment.dto.CommentRequestDTO;
import com.jyhun.board.domain.comment.dto.CommentResponseDTO;
import com.jyhun.board.domain.comment.entity.Comment;
import com.jyhun.board.domain.comment.repository.CommentRepository;
import com.jyhun.board.domain.member.constant.Role;
import com.jyhun.board.domain.member.entity.Member;
import com.jyhun.board.domain.member.repository.MemberRepository;
import com.jyhun.board.domain.post.entity.Post;
import com.jyhun.board.domain.post.repository.PostRepository;
import com.jyhun.board.global.exception.CustomException;
import com.jyhun.board.global.exception.ErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    private MemberRepository memberRepository;

    @Autowired
    private CommentService commentService;
    @Autowired
    private BoardRepository boardRepository;

    private Board board;
    private Member member;
    private Post post;
    private Comment comment;

    @BeforeEach
    void before() {
        board = new Board("게시판", "게시판 설명");
        boardRepository.save(board);

        member = new Member("회원", "email@email.com", "test1234", Role.USER);
        memberRepository.save(member);

        post = new Post("게시글", "게시글 내용", 0L, 0L);
        post.setMember(member);
        post.setBoard(board);
        postRepository.save(post);

        comment = new Comment("댓글 내용");
        comment.setPost(post);
        comment.setMember(member);
        commentRepository.save(comment);
    }

    @AfterEach
    void after() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 목록 조회 성공")
    void findComments_Success() {
        // when
        List<CommentResponseDTO> comments = commentService.findComments(post.getId());

        // then
        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals("댓글 내용", comments.get(0).getContent());
    }

    @Test
    @DisplayName("댓글 추가 성공")
    void addComment_Success() {
        // given
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO("댓글 내용");

        // when
        CommentResponseDTO result = commentService.addComment(post.getId(), commentRequestDTO, "email@email.com");

        // then
        assertNotNull(result);
        assertEquals(commentRequestDTO.getContent(), result.getContent());
        assertEquals(post.getId(), result.getPostId());
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void modifyComment_Success() {
        // given
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO("댓글 내용 2");

        // when
        CommentResponseDTO result = commentService.modifyComment(commentRequestDTO, comment.getId());

        // then
        assertNotNull(result);
        assertEquals("댓글 내용 2", result.getContent());
    }

    @Test
    @DisplayName("댓글 수정 실패: 댓글 없음")
    void modifyComment_NotFound() {
        // given
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
