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
@Table(name = "tblFeedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int classID;
    @Column
    private int tutorID;
    @Column
    private int rating;
    @Column
    private String content;
    @Column
    private String status;
    @Column
    private Date dateCreate;
}
