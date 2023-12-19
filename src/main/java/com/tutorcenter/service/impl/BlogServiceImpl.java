package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Blog;
import com.tutorcenter.repository.BlogRepository;
import com.tutorcenter.service.BlogService;

@Component
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll().stream().filter(b -> !b.isDeleted()).toList();
    }

    @Override
    public Optional<Blog> getBlogById(int id) {
        return blogRepository.findById(id);
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

}
