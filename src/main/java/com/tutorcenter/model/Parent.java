package com.tutorcenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "tblParent")
public class Parent extends User {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private int id;
    // @OneToOne
    // @JoinColumn(name = "userID")
    // private User user;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private String district;
    @Column
    private String province;
    @Column
    private String status;
}
