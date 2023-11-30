package com.tutorcenter.dto.blog;

import java.sql.Date;

import com.tutorcenter.model.Blog;

import lombok.Data;

@Data
public class BlogReqDto {
    private int managerId;
    private String thumbnail;
    private String category;
    private String title;
    private String content;
    private int status;
    private Date dateCreate;
    private Date dateModified;

    public void toBlog(Blog blog) {
        blog.setThumbnail(this.thumbnail);
        blog.setCategory(this.category);
        blog.setTitle(this.title);
        blog.setContent(this.content);
        blog.setStatus(this.status);
        blog.setDateCreate(this.dateCreate);
        blog.setDateModified(new Date(System.currentTimeMillis()));

    }
}
