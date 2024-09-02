package com.jyhun.board.domain.comment.entity;

import com.jyhun.board.domain.member.entity.Member;
import com.jyhun.board.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Comment(String content) {
        this.content = content;
    }

    // 댓글 수정
    public void updateComment(Comment comment) {
        this.content = comment.getContent();
    }

    public void setPost(Post post) {
        this.post = post;
        if(post != null){
            post.getCommentList().add(this);
        }
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
