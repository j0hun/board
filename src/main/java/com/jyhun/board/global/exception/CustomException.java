package com.jyhun.board.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
ErrorCode를 담을 클래스
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

}
