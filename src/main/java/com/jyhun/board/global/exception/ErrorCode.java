package com.jyhun.board.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
사용할 ErrorCode
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시판을 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    MEMBER_DUPLICATE(HttpStatus.CONFLICT, "이미 가입된 회원입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
