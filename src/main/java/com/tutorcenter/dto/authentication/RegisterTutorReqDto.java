package com.tutorcenter.dto.authentication;

import com.tutorcenter.constant.Role;
import com.tutorcenter.model.Tutor;

import lombok.Data;

@Data
public class RegisterTutorReqDto {
    private String email;
    private long idNumber;
    private String password;
    private String fullname;
    private String phone;
    private String address;
    private int districtId;
    private String gender;
    private String university;
    private String major;
    private String area;
    private String imgCertificate;
    private String imgAvatar;
    private String imgIdFront;
    private String imdIdBack;

    public void toTutor(Tutor tutor) {
        tutor.setEmail(this.email);
        tutor.setIdNumber(this.idNumber);
        tutor.setPassword(this.password);
        tutor.setFullname(this.fullname);
        tutor.setPhone(this.phone);
        tutor.setAddress(this.address);
        tutor.setRole(Role.TUTOR);
        tutor.setGender(this.gender);
        tutor.setUniversity(this.university);
        tutor.setMajor(this.major);
        tutor.setArea(this.area);
        tutor.setImgCertificate(this.imgCertificate);
        tutor.setImgAvatar(this.imgAvatar);
        tutor.setImgId(this.imgIdFront + "-" + this.imdIdBack);
    }
}
