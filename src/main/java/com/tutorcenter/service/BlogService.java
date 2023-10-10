package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Blog;

@Service
public interface BlogService {
    List<Blog> findAll();
}
