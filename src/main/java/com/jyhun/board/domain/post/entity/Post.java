package com.jyhun.board.domain.post.entity;

import com.jyhun.board.domain.board.entity.Board;
import com.jyhun.board.domain.comment.entity.Comment;
import com.jyhun.board.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

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

    // 좋아요수 증가
    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    public void setMember(Member member) {
        this.member = member;
    }
}
