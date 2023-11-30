package com.tutorcenter.model;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblBlog")
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "managerId")
    private Manager manager;
    @Column
    private String thumbnail;
    @Column
    private String category;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int status;
    @Column
    private Date dateCreate;
    @Column
    private Date dateModified;
    // @Column
    // private int createdBy;
    // @Column
    // private int updatedBy;
    @Column
    private boolean isDeleted;
}
