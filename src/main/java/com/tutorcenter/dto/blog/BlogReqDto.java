package com.tutorcenter.dto.blog;

import java.util.Date;

import com.tutorcenter.model.Blog;

import lombok.Data;

@Data
public class BlogReqDto {
    // private int managerId;
    private String thumbnail;
    private String category;
    private String title;
    private String content;
    // private int status;
    // private Date dateCreate;
    // private Date dateModified;

    public void toBlog(Blog blog) {
        blog.setThumbnail(this.thumbnail);
        blog.setCategory(this.category);
        blog.setTitle(this.title);
        blog.setContent(this.content);
        blog.setStatus(0);
        // blog.setDateCreate(this.dateCreate);
        blog.setDateModified(new Date(System.currentTimeMillis()));

    }
}
