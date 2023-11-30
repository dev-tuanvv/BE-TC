package com.tutorcenter.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.blog.BlogReqDto;
import com.tutorcenter.dto.blog.BlogResDto;
import com.tutorcenter.model.Blog;
import com.tutorcenter.service.BlogService;
import com.tutorcenter.service.ManagerService;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private ManagerService managerService;

    @GetMapping("")
    public ApiResponseDto<List<BlogResDto>> getAllBlogs() {
        try {
            List<BlogResDto> response = new ArrayList<>();
            for (Blog b : blogService.findAll()) {
                BlogResDto dto = new BlogResDto();
                dto.fromBlog(b);
                response.add(dto);
            }
            return ApiResponseDto.<List<BlogResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<BlogResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @GetMapping("/{id}")
    public ApiResponseDto<BlogResDto> getBlogById(@PathVariable int bId) {
        try {
            Blog blog = blogService.getBlogById(bId).orElse(null);
            BlogResDto dto = new BlogResDto();
            dto.fromBlog(blog);
            return ApiResponseDto.<BlogResDto>builder().data(dto).build();
        } catch (Exception e) {
            return ApiResponseDto.<BlogResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('manager:create')")
    @PutMapping("/create")
    public ApiResponseDto<Integer> createBlog(@RequestParam BlogReqDto dto) {
        try {
            Blog blog = new Blog();
            dto.toBlog(blog);
            blog.setDateCreate(new Date(System.currentTimeMillis()));
            blog.setManager(managerService.getManagerById(dto.getManagerId()).orElse(null));

            return ApiResponseDto.<Integer>builder().data(blogService.save(blog).getId()).build();
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('manager:delete')")
    @PutMapping("/delete/{id}")
    public ApiResponseDto<String> disableBlog(@PathVariable int id) {
        try {
            Blog blog = blogService.getBlogById(id).orElseThrow();
            blog.setDeleted(true);
            return ApiResponseDto.<String>builder().data(blog.getTitle()).build();
        } catch (Exception e) {
            return ApiResponseDto.<String>builder().responseCode("500").message(e.getMessage()).build();
        }
    }
}
