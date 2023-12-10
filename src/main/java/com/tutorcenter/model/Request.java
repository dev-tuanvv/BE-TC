package com.tutorcenter.model;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblRequest")
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "parentId")
    private Parent parent;
    @ManyToOne
    @JoinColumn(name = "managerId")
    private Manager manager;
    // @Column
    @OneToOne(mappedBy = "request")
    private Clazz clazz;
    @Column
    private String phone;
    @Column
    private String address;
    @ManyToOne
    @JoinColumn(name = "districtId")
    private District district;
    @Column
    private String daysOfWeek;
    @Column
    private String timeTutoring;
    @Column
    private int slots;
    @Column
    private int slotsLength;
    @Column
    private float tuition; // fee
    @Column
    private String notes; // tuoi, gioi tinh, vung mien, phuong phap day,...
    @Column
    private Date dateStart;
    @Column
    private Date dateEnd;
    @Column
    private Date dateCreate;
    @Column
    private Date dateModified;
    @Column
    private int status;
    @Column
    private String rejectReason;
    @JsonIgnore
    @Column
    private boolean isDeleted;
    @Column
    private String gender;
    @Column
    private String tutorLevel;
}
