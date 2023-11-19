package com.tutorcenter.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "tblTutor")
public class Tutor extends User {
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private int id;
    // @OneToOne
    // @JoinColumn(name = "userID")
    // private User user;
    @Column
    @OneToMany(mappedBy = "tutor")
    private Set<TutorApply> tutorApplies;
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
    @OneToMany(mappedBy = "tutor")
    private List<TutorSubject> subjects;
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
}
