package com.jyhun.board.domain.post.controller;

import com.jyhun.board.domain.post.dto.PostResponseDTO;
import com.jyhun.board.domain.post.dto.PostSearchDTO;
import com.jyhun.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PostPageController {

    private final PostService postService;

    @RequestMapping("/boards/{boardId}")
    public String getPosts(Model model,
                           @PathVariable Long boardId,
                           @RequestParam(required = false) String searchKey,
                           @RequestParam(required = false) String searchValue,
                           @PageableDefault(size = 10) Pageable pageable) {
        PostSearchDTO postSearchDTO = new PostSearchDTO(searchKey, searchValue);
        log.info("게시글 목록 페이징 조회, 검색 조건: {}, 페이지: {}", postSearchDTO, pageable);

        Page<PostResponseDTO> posts = postService.findPosts(boardId, postSearchDTO, pageable);

        model.addAttribute("posts", posts);
        model.addAttribute("boardId", boardId);
        model.addAttribute("postSearchDTO", postSearchDTO);
        model.addAttribute("maxPage", 5);

        return "posts/posts";
    }


    @RequestMapping("/posts/{postId}")
    public String getPostById(Model model, @PathVariable Long postId) {
        log.info("ID {}의 게시글 조회 요청", postId);
        PostResponseDTO post = postService.findPostById(postId);
        log.info("ID {}의 게시글 조회 성공", postId);
        postService.increaseViewCount(postId);
        log.info("ID {}의 조회수 증가 성공", postId);
        model.addAttribute("post",post);
        return "posts/post";
    }

    @RequestMapping("/boards/{boardId}/createPost")
    public String createPostPage(Model model, @PathVariable Long boardId) {
        model.addAttribute("boardId", boardId);
        return "posts/createPost";
    }

    @RequestMapping("/posts/{postId}/editPost")
    public String editPostPage(Model model, @PathVariable Long postId) {
        model.addAttribute("postId",postId);
        return "posts/editPost";
    }

}
