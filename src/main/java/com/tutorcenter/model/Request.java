package com.tutorcenter.model;

import java.sql.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblRequest")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "parentID")
    private Parent parent;
    @ManyToOne
    @JoinColumn(name = "managerID")
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
    // @Column
    // private String level;
    @Column
    @OneToMany(mappedBy = "request")
    private Set<RequestSubject> subjects;
    // @Column
    // private int amountStudent;
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
    private Date datemodified;
    @Column
    private String status;
    @Column
    private String rejectReason;
    @Column
    private boolean isDeleted;
}
