package com.jyhun.board.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
RestController 전역에서 발생하는 Custom Error를 잡아줄 Handler
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException ex) {
        return ErrorResponseEntity.toEntity(ex.getErrorCode());
    }

}
