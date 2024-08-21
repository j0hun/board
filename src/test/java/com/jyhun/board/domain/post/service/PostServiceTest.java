package com.jyhun.board.domain.post.service;

import com.jyhun.board.domain.board.entity.Board;
import com.jyhun.board.domain.board.repository.BoardRepository;
import com.jyhun.board.domain.post.dto.PostRequestDTO;
import com.jyhun.board.domain.post.dto.PostResponseDTO;
import com.jyhun.board.domain.post.dto.PostSearchDTO;
import com.jyhun.board.domain.post.entity.Post;
import com.jyhun.board.domain.post.repository.PostRepository;
import com.jyhun.board.global.exception.CustomException;
import com.jyhun.board.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 목록 조회")
    void findPosts() {
        // given
        Board board = new Board("게시판", "게시판 설명");
        boardRepository.save(board);

        Post post1 = new Post("제목 1", "내용 1", 0L, 0L);
        post1.setBoard(board);
        postRepository.save(post1);

        Post post2 = new Post("제목 2", "내용 2", 0L, 0L);
        post2.setBoard(board);
        postRepository.save(post2);

        PostSearchDTO postSearchDTO = new PostSearchDTO(null, null);
        Pageable pageable = Pageable.ofSize(10);

        // when
        Page<PostResponseDTO> result = postService.findPosts(board.getId(), postSearchDTO, pageable);

        // then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("게시글 제목 검색")
    void findPostSearchTitle() {
        // given
        Board board = new Board("게시판", "게시판 설명");
        boardRepository.save(board);

        Post post1 = new Post("제목 1", "내용 1", 0L, 0L);
        post1.setBoard(board);
        postRepository.save(post1);

        Post post2 = new Post("제목 2", "내용 2", 0L, 0L);
        post2.setBoard(board);
        postRepository.save(post2);

        Post post3 = new Post("title", "content", 0L, 0L);
        post3.setBoard(board);
        postRepository.save(post3);

        PostSearchDTO postSearchDTO = new PostSearchDTO("title", "제목");
        Pageable pageable = Pageable.ofSize(10);

        // when
        Page<PostResponseDTO> result = postService.findPosts(board.getId(), postSearchDTO, pageable);

        // then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    @DisplayName("게시글 ID로 조회 성공")
    void findPostById_Success() {
        // given
        Board board = new Board("게시판", "게시판 설명");
        boardRepository.save(board);

        Post post = new Post("제목", "내용", 0L, 0L);
        post.setBoard(board);
        postRepository.save(post);

        Long postId = post.getId();

        // when
        PostResponseDTO result = postService.findPostById(postId);

        // then
        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals("제목", result.getTitle());
        assertEquals("내용", result.getContent());
    }

    @Test
    @DisplayName("게시글 ID로 조회 실패")
    void findPostById_NotFound() {
        // given
        Long postId = 1L;

        // when
        CustomException thrown = assertThrows(CustomException.class, () -> postService.findPostById(postId));

        // then
        assertEquals(ErrorCode.POST_NOT_FOUND, thrown.getErrorCode());
    }

    @Test
    @DisplayName("게시글 추가 성공")
    void addPost_Success() {
        // given
        Board board = new Board("게시판", "게시판 설명");
        boardRepository.save(board);

        PostRequestDTO postRequestDTO = new PostRequestDTO("제목", "내용");

        // when
        PostResponseDTO result = postService.addPost(board.getId(), postRequestDTO);

        // then
        assertNotNull(result);
        assertEquals("제목", result.getTitle());
        assertEquals("내용", result.getContent());
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("게시글 추가 실패 - 게시판 없음")
    void addPost_BoardNotFound() {
        // given
        Long invalidBoardId = 1L;
        PostRequestDTO postRequestDTO = new PostRequestDTO("제목", "내용");

        // when
        CustomException thrown = assertThrows(CustomException.class, () -> postService.addPost(invalidBoardId, postRequestDTO));

        // then
        assertEquals(ErrorCode.BOARD_NOT_FOUND, thrown.getErrorCode());
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void modifyPost_Success() {
        // given
        Board board = new Board("게시판", "게시판 설명");
        boardRepository.save(board);

        Post post = new Post("제목", "내용", 0L, 0L);
        post.setBoard(board);
        postRepository.save(post);

        PostRequestDTO postRequestDTO = new PostRequestDTO("수정된 제목", "수정된 내용");

        // when
        PostResponseDTO result = postService.modifyPost(postRequestDTO, post.getId());

        // then
        assertNotNull(result);
        assertEquals("수정된 제목", result.getTitle());
        assertEquals("수정된 내용", result.getContent());
    }

    @Test
    @DisplayName("게시글 수정 실패 - 게시글 없음")
    void modifyPost_NotFound() {
        // given
        Long invalidPostId = 1L;
        PostRequestDTO postRequestDTO = new PostRequestDTO("제목", "내용");

        // when
        CustomException thrown = assertThrows(CustomException.class, () -> postService.modifyPost(postRequestDTO, invalidPostId));

        // then
        assertEquals(ErrorCode.POST_NOT_FOUND, thrown.getErrorCode());
    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void deletePost_Success() {
        // given
        Board board = new Board("게시판", "게시판 설명");
        boardRepository.save(board);

        Post post = new Post("제목", "내용", 0L, 0L);
        post.setBoard(board);
        postRepository.save(post);

        // when
        postService.deletePost(post.getId());

        // then
        assertFalse(postRepository.findById(post.getId()).isPresent());
    }

    @Test
    @DisplayName("게시글 삭제 실패 - 게시글 없음")
    void deletePost_NotFound() {
        // given
        Long invalidPostId = 1L;

        // when
        CustomException thrown = assertThrows(CustomException.class, () -> postService.deletePost(invalidPostId));

        // then
        assertEquals(ErrorCode.POST_NOT_FOUND, thrown.getErrorCode());
    }

    @Test
    @DisplayName("조회수 증가 성공")
    void increaseViewCount_Success() {
        // given
        Board board = new Board("게시판", "게시판 설명");
        boardRepository.save(board);

        Post post = new Post("제목", "내용", 0L, 0L);
        post.setBoard(board);
        postRepository.save(post);

        // when
        postService.increaseViewCount(post.getId());

        // then
        Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
        assertEquals(1L, updatedPost.getViewCount());
    }

    @Test
    @DisplayName("좋아요 수 증가 성공")
    void increaseLikeCount_Success() {
        // given
        Board board = new Board("게시판", "게시판 설명");
        boardRepository.save(board);

        Post post = new Post("제목", "내용", 0L, 0L);
        post.setBoard(board);
        postRepository.save(post);

        // when
        postService.increaseLikeCount(post.getId());

        // then
        Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
        assertEquals(1L, updatedPost.getLikeCount());
    }
}
