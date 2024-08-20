package com.jyhun.board.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSearchDTO {

    private String searchKey; // 제목, 작성자 등
    private String searchValue; // 키워드 검색

}
