package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Blog;

@Service
public interface BlogService {
    List<Blog> findAll();

    Optional<Blog> getBlogById(int id);

    Blog save(Blog blog);
}
