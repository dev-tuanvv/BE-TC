package com.tutorcenter.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblBlog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int managerID;
    @Column
    private String Thumbnail;
    @Column
    private String Category;
    @Column
    private String Title;
    @Column
    private String Content;
    @Column
    private String Status;
    @Column
    private Date dateCreate;
    @Column
    private int createdBy;
    @Column
    private int updatedBy;
    @Column
    private boolean isDeleted;
}
