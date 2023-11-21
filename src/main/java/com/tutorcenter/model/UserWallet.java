package com.tutorcenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblUserWallet")
public class UserWallet {
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column
    private float balance;
}
