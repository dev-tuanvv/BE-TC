package com.tutorcenter.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tblDistrict")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "provinceId")
    private Province province;
    @Column
    @OneToMany(mappedBy = "district")
    private Set<Tutor> tutors;
    @Column
    @OneToMany(mappedBy = "district")
    private Set<Request> requests;
}
