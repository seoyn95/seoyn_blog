package com.github.seoyn95.blog.service;

import com.github.seoyn95.blog.domain.Post;
import com.github.seoyn95.blog.repository.PostRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;


@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public Long write(Post post) {
        validateDupicateSlug(post);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        return post.getId();

    }

    private void validateDupicateSlug(Post post) {
        postRepository.findBySlug(post.getSlug())
                .ifPresent(p -> {
                    throw new IllegalStateException("이미 존재하는 주소입니다.");
                });
    }

    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> findOne(String slug) {
        return postRepository.findBySlug(slug);
    }

}
