package com.jyhun.board.domain.post.repository;

import com.jyhun.board.domain.post.dto.PostSearchDTO;
import com.jyhun.board.domain.post.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.jyhun.board.domain.post.entity.QPost.post;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression search(String searchKey, String searchValue) {
        if (searchKey == null || searchValue == null) {
            return null;
        }
        if (searchKey.equals("title")) {
            return post.title.like("%" + searchValue + "%");
        }
        return null;
    }

    @Override
    public Page<Post> findPosts(PostSearchDTO postSearchDTO, Pageable pageable) {
        List<Post> postList = queryFactory
                .selectFrom(post)
                .where(search(postSearchDTO.getSearchKey(), postSearchDTO.getSearchValue()))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(post.count())
                .from(post)
                .where(search(postSearchDTO.getSearchKey(), postSearchDTO.getSearchValue()))
                .fetchOne();

        return new PageImpl<>(postList, pageable, totalCount);
    }
}
