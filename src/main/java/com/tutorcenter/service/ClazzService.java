package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.dto.clazz.SearchReqDto;
import com.tutorcenter.dto.clazz.SearchResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Tutor;

@Service
public interface ClazzService {
    List<Clazz> findAll();

    List<SearchResDto> search(int limit, int offset, SearchReqDto req, String order);

    Optional<Clazz> getClazzById(int id);

    Clazz save(Clazz clazz);

    List<Clazz> getClazzByParentId(int pId);

    List<Clazz> getClazzByManagerId(int pId);

    List<Clazz> getClazzByTutorId(int pId);

    List<Clazz> getClazzBySubjectId(int sId);

    List<Clazz> getClazzByLevel(String level);

    List<Clazz> getClazzByDistrict(int dId);

    List<Clazz> getClazzByStatus(int status);

    int countNoClassByTutor(Tutor tutor);

    List<Clazz> findByDistrict(District district);
}
