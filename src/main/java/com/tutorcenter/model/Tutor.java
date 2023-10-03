package com.tutorcenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblTutor")
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String userID;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private String district;
    @Column
    private String province;
    @Column
    private String iDnumber;
    @Column
    private String university;
    @Column
    private String major;
    @Column
    private String subject;
    @Column
    private float grade;
    @Column
    private String area;
    @Column
    private String imgAvatar;
    @Column
    private String imgCertificate;
    @Column
    private String imgID;
}
