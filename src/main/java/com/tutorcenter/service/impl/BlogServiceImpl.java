package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Blog;
import com.tutorcenter.repository.BlogRepository;
import com.tutorcenter.service.BlogService;

@Component
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository BlogRepository;

    @Override
    public List<Blog> findAll() {
        return BlogRepository.findAll();
    }

}
