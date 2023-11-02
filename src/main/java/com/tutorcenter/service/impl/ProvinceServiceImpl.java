package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Province;
import com.tutorcenter.repository.ProvinceRepository;
import com.tutorcenter.service.ProvinceService;

@Component
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    ProvinceRepository provinceRepository;

    @Override
    public List<Province> findAll() {
        return provinceRepository.findAll();
    }

    @Override
    public Optional<Province> getProvinceById(int id) {
        return provinceRepository.findById(id);
    }

}
