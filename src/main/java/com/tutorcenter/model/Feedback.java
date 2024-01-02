package com.tutorcenter.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblFeedback")
public class Feedback implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clazzId", referencedColumnName = "id")
    private Clazz clazz;
    @Column
    private int rating;
    @Column
    private String content;
    @Column
    private int status;
    @Column
    private Date dateCreate;
    @Column
    private boolean isDeleted;
    @Column
    private int professionalSkill;
    @Column
    private int workingStyle;
    @Column
    private int pedagogicalSkill;
    @Column
    private int supportOT;
    @Column
    private int courseCover;
}
