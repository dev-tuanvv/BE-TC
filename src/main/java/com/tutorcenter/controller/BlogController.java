package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Blog;
import com.tutorcenter.repository.BlogRepository;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @GetMapping("")
    public List<Blog> getAllBlogs() {
        return null;
    }
}
