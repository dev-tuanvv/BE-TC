package com.tutorcenter.dto.tutor;

import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Tutor;

import lombok.Data;

@Data
public class TutorDetailResDto {
    private String tutorName;
    private List<SubjectLevelResDto> subjects;
    private String phone;
    private String address;
    private String areas;
    private String gender;
    private String districtName;
    private String provinceName;
    private String university;
    private String tutorLevel;
    private String major;
    private String imgAvatar;
    private String imgCert;
    private float rating;

    public void fromTutor(Tutor tutor) {
        this.tutorName = tutor.getFullname();
        this.gender = tutor.getGender();
        this.phone = tutor.getPhone();
        this.address = tutor.getAddress();
        this.districtName = tutor.getDistrict().getName();
        this.provinceName = tutor.getDistrict().getProvince().getName();
        this.university = tutor.getUniversity();
        this.tutorLevel = tutor.getTutorLevel();
        this.major = tutor.getMajor();
        this.imgAvatar = tutor.getImgAvatar();
        this.imgCert = tutor.getImgCertificate();
    }
}
