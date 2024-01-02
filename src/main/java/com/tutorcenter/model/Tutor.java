package com.tutorcenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "tblTutor")
@EqualsAndHashCode(callSuper = true)
public class Tutor extends User {
    @Column
    private String phone;
    @Column
    private String address;
    @ManyToOne
    @JoinColumn(name = "districtId")
    private District district;
    @Column
    private String idNumber;
    @Column
    private String university;
    @Column
    private String major;
    @Column
    private String imgAvatar;
    @Column
    private String imgCertificate;
    @Column
    private String imgId;
    @Column
    private int status;
    @Column
    private String gender;
    @Column
    private String area;
    @Column
    private String tutorLevel;
    @Column
    private int testLevel;
}
