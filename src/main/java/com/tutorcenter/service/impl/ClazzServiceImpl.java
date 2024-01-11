package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.dto.clazz.SearchReqDto;
import com.tutorcenter.dto.clazz.SearchResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.TutorDistrict;
import com.tutorcenter.repository.ClazzRepository;
import com.tutorcenter.repository.TutorDistrictRepository;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.TutorService;
import com.tutorcenter.service.TutorSubjectService;

@Component
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    ClazzRepository clazzRepository;
    @Autowired
    private TutorService tutorService;
    @Autowired
    TutorDistrictRepository tutorDistrictRepository;

    @Override
    public List<Clazz> findAll() {
        return clazzRepository.findAll().stream().filter(b -> !b.isDeleted()).toList();
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
        List<Clazz> list = new ArrayList<>();

        for (Clazz c : clazzRepository.findAll()) {
            if (c.getRequest().getManager() != null && c.getRequest().getManager().getId() == mId)
                list.add(c);
        }
        return list;
    }

    @Override
    public List<Clazz> getClazzByTutorId(int tId) {
        List<Clazz> list = new ArrayList<>();

        for (Clazz c : clazzRepository.findAll()) {
            if (c.getTutor() != null && c.getTutor().getId() == tId)
                list.add(c);
        }
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
        List<Clazz> list = new ArrayList<>();

        for (Clazz c : clazzRepository.findAll()) {
            if (c.getRequest().getDistrict().getId() == dId)
                list.add(c);
        }
        return list;
    }

    @Override
    public List<Clazz> getClazzByStatus(int status) {
        List<Clazz> list = new ArrayList<>();

        for (Clazz c : clazzRepository.findAll()) {
            if (c.getStatus() == status)
                list.add(c);
        }
        return list;
    }

    @Override
    public int countNoClassByTutor(Tutor tutor) {
        return clazzRepository.findByTutor(tutor).size();
    }

    @Override
    public List<Clazz> findByTutorDistrict(Tutor tutor) {
        List<Clazz> response = new ArrayList<>();
        List<TutorDistrict> tds = tutorDistrictRepository.findByTutor(tutor);
        for (TutorDistrict td : tds) {
            for (Clazz c : clazzRepository.findByStatus(1)) {
                if (c.getRequest().getDistrict().getId() == td.getDistrict().getId())
                    response.add(c);
            }
        }
        return response;
    }

}
