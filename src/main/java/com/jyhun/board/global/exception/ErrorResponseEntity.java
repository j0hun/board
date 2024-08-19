package com.jyhun.board.global.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

/*
CustomError를 담을 ResponseEntity:
 */
@Data
@Builder
public class ErrorResponseEntity {

    private int status;
    private String name;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .status(errorCode.getHttpStatus().value())
                        .name(errorCode.name())
                        .message(errorCode.getMessage())
                        .build());
    }

}
