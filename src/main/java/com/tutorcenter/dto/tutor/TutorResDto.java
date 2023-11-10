package com.tutorcenter.dto.tutor;

import com.tutorcenter.model.Tutor;

import lombok.Data;

@Data
public class TutorResDto {

    private String tutorName;
    private String gender;
    private String districtName;
    private String provinceName;
    private String university;
    private String major;
    private String imgCert;
    private String imgAvatar;

    public void fromTutor(Tutor tutor) {
        this.tutorName = tutor.getFullname();
        this.gender = tutor.getGender();
        this.districtName = tutor.getDistrict().getName();
        this.provinceName = tutor.getDistrict().getProvince().getName();
        this.university = tutor.getUniversity();
        this.major = tutor.getMajor();
        this.imgCert = tutor.getImgCertificate();
        this.imgAvatar = tutor.getImgAvatar();
    }
}
