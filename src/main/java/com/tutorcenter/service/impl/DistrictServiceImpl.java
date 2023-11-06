package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.District;
import com.tutorcenter.repository.DistrictRepository;
import com.tutorcenter.service.DistrictService;

@Component
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    DistrictRepository districtRepository;

    @Override
    public List<District> findAll() {
        return districtRepository.findAll();
    }

    @Override
    public List<District> getDistrictsByProvince(int pId) {
        List<District> list = new ArrayList<>();
        for (District d : findAll()) {
            if (d.getProvince().getId() == pId)
                list.add(d);
        }
        return list;
    }

    @Override
    public Optional<District> getDistrictById(int id) {
        return districtRepository.findById(id);
    }

}
