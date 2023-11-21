package com.tutorcenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "tbl_Manager")
@Data
@EqualsAndHashCode(callSuper = true)
public class Manager extends User {
    @Column
    private String phone;
    @Column
    private int status;
}
