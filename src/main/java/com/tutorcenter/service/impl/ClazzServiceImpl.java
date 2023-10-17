package com.tutorcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.dto.clazz.SearchReqDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.repository.ClazzRepository;
import com.tutorcenter.service.ClazzService;

@Component
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    ClazzRepository clazzRepository;

    @Override
    public List<Clazz> findAll() {
        return clazzRepository.findAll();
    }

     public List<Clazz> search(int limit, int offset, SearchReqDto req, String order) {
        // đến đây dùng JPA k dùng kiểu Object SearchReqDto(req) được nữa
        // đưa về kiểu dữa liệu cuối cùng của MySQL hiểu ví dụ: int(limit) hoặc string(req.condition)
        return clazzRepository.search(limit,offset);
    }

}
