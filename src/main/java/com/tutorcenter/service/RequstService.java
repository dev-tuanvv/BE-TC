package com.tutorcenter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Requst;

@Service
public interface RequstService {
    List<Requst> findAll();
}
