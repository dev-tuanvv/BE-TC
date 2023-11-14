package com.tutorcenter.dto.tutor;

import java.util.List;

import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Tutor;

import lombok.Data;

@Data
public class TutorProfileResDto {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String districtName;
    private String provinceName;
    private long idNumber;
    private String gender;
    private String university;
    private String major;
    private String imgAvatar;
    private String imgCertificate;
    private String imgId;
    private List<SubjectLevelResDto> subjects;

    public void fromTutor(Tutor tutor) {
        this.fullName = tutor.getFullname();
        this.email = tutor.getEmail();
        this.phone = tutor.getPhone();
        this.address = tutor.getAddress();
        this.idNumber = tutor.getIdNumber();
        this.gender = tutor.getGender();
        this.university = tutor.getUniversity();
        this.imgAvatar = tutor.getImgAvatar();
        this.imgId = tutor.getImgId();
        this.districtName = tutor.getDistrict().getName();
        this.provinceName = tutor.getDistrict().getProvince().getName();
        this.university = tutor.getUniversity();
        this.major = tutor.getMajor();
        this.imgCertificate = tutor.getImgCertificate();
    }
}
