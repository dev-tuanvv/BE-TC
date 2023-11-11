package com.tutorcenter.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.dto.clazz.SearchReqDto;
import com.tutorcenter.dto.clazz.SearchResDto;
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

    public List<SearchResDto> search(int limit, int offset, SearchReqDto req, String order) {
        // đến đây dùng JPA k dùng kiểu Object SearchReqDto(req) được nữa
        // đưa về kiểu dữa liệu cuối cùng của MySQL hiểu ví dụ: int(limit) hoặc
        // string(req.condition)
        return clazzRepository.search(limit, offset);
    }

    @Override
    public Optional<Clazz> getClazzById(int id) {
        return clazzRepository.findById(id);
    }

    @Override
    public Clazz save(Clazz clazz) {
        return clazzRepository.save(clazz);
    }

    @Override
    public List<Clazz> getClazzByParentId(int pId) {
        List<Clazz> list = findAll().stream()
                .filter(clazz -> clazz.getRequest().getParent().getId() == pId)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Clazz> getClazzByManagerId(int mId) {
        List<Clazz> list = findAll().stream()
                .filter(clazz -> clazz.getRequest().getManager().getId() == mId)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Clazz> getClazzByTutorId(int tId) {
        List<Clazz> list = findAll().stream()
                .filter(clazz -> clazz.getTutor().getId() == tId)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Clazz> getClazzBySubjectId(int sId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClazzBySubjectId'");
    }

    @Override
    public List<Clazz> getClazzByLevel(String level) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClazzByLevel'");
    }

    @Override
    public List<Clazz> getClazzByDistrict(int dId) {
        List<Clazz> list = findAll().stream()
                .filter(clazz -> clazz.getRequest().getDistrict().getId() == dId)
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Clazz> getClazzByStatus(int status) {
        List<Clazz> list = findAll().stream()
                .filter(clazz -> clazz.getRequest().getStatus() == status)
                .collect(Collectors.toList());
        return list;
    }

}
