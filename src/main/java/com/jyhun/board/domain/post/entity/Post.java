package com.jyhun.board.domain.post.entity;

import com.jyhun.board.domain.board.entity.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 제목

    private String content; // 내용

    private Long viewCount; // 조회수

    private Long likeCount; // 좋아요수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Post(String title, String content, Long viewCount, Long likeCount) {
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }

    // 게시글 수정
    public void updatePost(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    // 조회수 증가
    public void increaseViewCount() {
        this.viewCount += 1;
    }

}
