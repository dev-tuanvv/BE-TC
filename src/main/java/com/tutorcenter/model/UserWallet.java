package com.tutorcenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "tblUserWallet")
public class UserWallet extends User {
    @Column
    private float balance;
}
