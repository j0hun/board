package com.jyhun.board.domain.domain.service;

import com.jyhun.board.domain.domain.dto.CommentRequestDTO;
import com.jyhun.board.domain.domain.dto.CommentResponseDTO;
import com.jyhun.board.domain.domain.entity.Comment;
import com.jyhun.board.domain.domain.repository.CommentRepository;
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

    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findComments() {
        log.info("findComments 메서드 호출");

        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentResponseDTO commentResponseDTO = CommentResponseDTO.toDTO(comment);
            commentResponseDTOList.add(commentResponseDTO);
        }

        return commentResponseDTOList;
    }

    @Transactional
    public CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO) {
        log.info("addComment 메서드 호출");

        Comment comment = commentRequestDTO.toEntity();
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

}
