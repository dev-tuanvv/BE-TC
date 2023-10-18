package com.tutorcenter.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class BlogDTO {
    private int id;
    private int managerID;
    private String Thumbnail;
    private String Category;
    private String Title;
    private String Content;
    private String Status;
    private Date dateCreate;
    private int createdBy;
    private int updatedBy;
}
