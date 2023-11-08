package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Province;
import com.tutorcenter.service.DistrictService;
import com.tutorcenter.service.ProvinceService;

@RestController
@RequestMapping("/api/district")
public class DistrictController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private DistrictService districtService;

    @GetMapping("/province")
    public ApiResponseDto<List<Province>> getAllProvince() {

        return ApiResponseDto.<List<Province>>builder().data(provinceService.findAll()).build();
    }

    @GetMapping("/province/{pId}")
    public ApiResponseDto<List<District>> getDistrictsByProvince(@PathVariable int pId) {
        return ApiResponseDto.<List<District>>builder().data(districtService.getDistrictsByProvince(pId)).build();
    }
}
