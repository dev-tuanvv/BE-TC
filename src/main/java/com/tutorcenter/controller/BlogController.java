package com.tutorcenter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Blog;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @GetMapping("")
    public List<Blog> getAllBlogs() {
        return null;
    }
}
