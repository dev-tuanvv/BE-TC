package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Province;

@Service
public interface ProvinceService {
    List<Province> findAll();

    Optional<Province> getProvinceById(int id);

}
