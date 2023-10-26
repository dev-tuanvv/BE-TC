package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.dto.clazz.SearchReqDto;
import com.tutorcenter.dto.clazz.SearchResDto;
import com.tutorcenter.model.Clazz;

@Service
public interface ClazzService {
    List<Clazz> findAll();

    List<SearchResDto> search(int limit, int offset, SearchReqDto req, String order);

    Optional<Clazz> getClazzById(int id);

    Clazz save(Clazz clazz);
}
