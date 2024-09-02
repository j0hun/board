package com.jyhun.board.domain.comment.service;

import com.jyhun.board.domain.comment.dto.CommentRequestDTO;
import com.jyhun.board.domain.comment.dto.CommentResponseDTO;
import com.jyhun.board.domain.comment.entity.Comment;
import com.jyhun.board.domain.comment.repository.CommentRepository;
import com.jyhun.board.domain.member.entity.Member;
import com.jyhun.board.domain.member.repository.MemberRepository;
import com.jyhun.board.domain.post.entity.Post;
import com.jyhun.board.domain.post.repository.PostRepository;
import com.jyhun.board.global.exception.CustomException;
import com.jyhun.board.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findComments(Long postId) {
        log.info("findComments 메서드 호출");

        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentResponseDTO commentResponseDTO = CommentResponseDTO.toDTO(comment);
            commentResponseDTOList.add(commentResponseDTO);
        }

        return commentResponseDTOList;
    }

    @Transactional
    public CommentResponseDTO addComment(Long postId, CommentRequestDTO commentRequestDTO,String email) {
        log.info("addComment 메서드 호출");

        Post post = postRepository.findById(postId).orElseThrow(() -> {
            log.error("ID가 {}인 게시글을 찾을 수 없습니다.", postId);
            return new CustomException(ErrorCode.POST_NOT_FOUND);
        });

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> {
            log.error("이메일이 {}인 회원을 찾을 수 없습니다.", email);
            return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        });

        Comment comment = commentRequestDTO.toEntity();
        comment.setPost(post);
        comment.setMember(member);
        Comment savedComment = commentRepository.save(comment);

        log.info("댓글 추가 완료, 댓글 ID: {}", savedComment.getId());
        return CommentResponseDTO.toDTO(savedComment);
    }

    @Transactional
    public CommentResponseDTO modifyComment(CommentRequestDTO commentRequestDTO, Long commentId) {
        log.info("modifyComment 메서드 호출");

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.error("ID가 {}인 댓글을 찾을 수 없습니다.", commentId);
                    return new CustomException(ErrorCode.COMMENT_NOT_FOUND);
                });
        Comment updatedComment = commentRequestDTO.toEntity();
        comment.updateComment(updatedComment);
        log.info("댓글 수정 완료, 댓글 ID: {}", commentId);
        return CommentResponseDTO.toDTO(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        log.info("deleteComment 메서드 호출");

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.error("ID가 {}인 댓글을 찾을 수 없습니다.", commentId);
                    return new CustomException(ErrorCode.COMMENT_NOT_FOUND);
                });
        commentRepository.delete(comment);
        log.info("댓글 삭제 완료, 댓글 ID: {}", commentId);
    }

}
