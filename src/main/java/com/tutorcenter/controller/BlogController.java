package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Blog;
import com.tutorcenter.repository.BlogRepository;
import com.tutorcenter.service.BlogService;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("")
    public List<Blog> getAllBlogs() {
        return null;
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Blog> disableBlog(@PathVariable int id) {
        Blog blog = blogService.getBlogById(id).orElseThrow();
        blog.setDeleted(true);
        return ResponseEntity.ok(blogService.save(blog));
    }
}
