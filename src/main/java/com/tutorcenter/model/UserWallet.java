package com.tutorcenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "tblUserWallet")
public class UserWallet extends User {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private int id;
    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "userId", referencedColumnName = "id")
    // private User user;
    @Column
    private float balance;
}
