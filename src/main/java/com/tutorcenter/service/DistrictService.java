package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.District;

@Service
public interface DistrictService {
    List<District> findAll();

    List<District> getDistrictsByProvince(int pId);

    Optional<District> getDistrictById(int id);

}