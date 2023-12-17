package com.tutorcenter.dto.blog;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tutorcenter.model.Blog;

import lombok.Data;

@Data
public class BlogResDto {
    private int id;
    private int managerId;
    private String thumbnail;
    private String category;
    private String title;
    private String content;
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dateCreate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date dateModified;

    public void fromBlog(Blog blog) {
        this.id = blog.getId();
        this.managerId = blog.getManager().getId();
        this.thumbnail = blog.getThumbnail();
        this.category = blog.getCategory();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.status = blog.getStatus();
        this.dateCreate = blog.getDateCreate();
        this.dateModified = blog.getDateModified();
    }
}
