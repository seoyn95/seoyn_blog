package com.github.seoyn95.blog.controller;

import com.github.seoyn95.blog.service.PostService;
import com.github.seoyn95.blog.domain.Post;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller 
public class PostController {
    

    private final PostService postService;

    @Autowired 
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    @ResponseBody
    public String write(PostForm form) {
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setSlug(form.getSlug());
        post.setSummary(form.getSummary());
        post.setContent(form.getContent());

        Long id = postService.write(post);
        return "게시글이 등록되었습니다. (id: " + id + ")";
    }


    @GetMapping("/posts")
    @ResponseBody
    public List<Post> list() {
        return postService.findPosts();
    }

    @GetMapping("/posts/detail")
    @ResponseBody
    public Post detail(@RequestParam("slug") String slug) {
        return postService.findOne(slug).get();
    }

}
