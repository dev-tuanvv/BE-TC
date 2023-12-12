package com.tutorcenter.dto.authentication;

import java.util.List;

import com.tutorcenter.constant.Role;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Tutor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterTutorReqDto {
    @NotEmpty
    private String email;
    private String idNumber;
    @NotEmpty
    private String password;
    @NotEmpty
    private String fullname;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String address;
    @NotNull
    private int districtId;
    @NotEmpty
    private String gender;
    private String university;
    private String major;
    private String area;
    private String imgCertificate;
    private String imgAvatar;
    private String imgIdFront;
    private String imdIdBack;
    private List<Integer> subjects;

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
        tutor.setImgId(this.imgIdFront + "~" + this.imdIdBack);
    }
}
