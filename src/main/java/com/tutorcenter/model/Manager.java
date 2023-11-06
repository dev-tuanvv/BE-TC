package com.tutorcenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "tbl_Manager")
public class Manager extends User {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private int id;
    // @OneToOne
    // @JoinColumn(name = "userID")
    // private User user;
    @Column
    private String phone;
    @Column
    private int status;
}
