package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.tutorApply.TutorApplyResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.TutorApply;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.TutorApplyService;
import com.tutorcenter.service.TutorService;

@RestController
@RequestMapping("/api/tutorApply")
public class TutorApplyController {
    @Autowired
    private TutorApplyService tutorApplyService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private TutorService tutorService;

    @GetMapping("/")
    public List<TutorApply> getAll() {
        return tutorApplyService.findAll();
    }

    @GetMapping("/clazz/{id}")
    public ApiResponseDto<List<TutorApplyResDto>> getTutorAppliesByClazzId(@PathVariable int id) {
        List<TutorApply> taList = tutorApplyService.getTutorAppliesByClazzId(id);
        List<TutorApplyResDto> response = new ArrayList<>();

        for (TutorApply ta : taList) {
            TutorApplyResDto dto = new TutorApplyResDto();
            dto.fromTutorApply(ta);
            response.add(dto);

        }

        return ApiResponseDto.<List<TutorApplyResDto>>builder().data(response).build();
    }

    @GetMapping("/tutor/{id}")
    public ApiResponseDto<List<TutorApplyResDto>> getTutorAppliesByTutorId(@PathVariable int id) {
        List<TutorApply> taList = tutorApplyService.getTutorAppliesByTutorId(id);
        List<TutorApplyResDto> response = new ArrayList<>();

        for (TutorApply ta : taList) {
            TutorApplyResDto dto = new TutorApplyResDto();
            dto.fromTutorApply(ta);
            response.add(dto);

        }

        return ApiResponseDto.<List<TutorApplyResDto>>builder().data(response).build();
    }

    @PostMapping("/create")
    public ApiResponseDto<TutorApplyResDto> create(@RequestParam(name = "clazzId") int cId,
            @RequestParam(name = "tutorId") int tId) {
        TutorApply tutorApply = new TutorApply();
        Clazz clazz = clazzService.getClazzById(cId).orElse(null);
        Tutor tutor = tutorService.getTutorById(tId).orElse(null);

        tutorApply.setClazz(clazz);
        tutorApply.setTutor(tutor);
        tutorApply.setDeleted(false);
        tutorApply.setStatus(0);

        TutorApplyResDto dto = new TutorApplyResDto();
        dto.fromTutorApply(tutorApplyService.save(tutorApply));

        return ApiResponseDto.<TutorApplyResDto>builder().data(dto).build();
    }

    // @PutMapping("/updateStatus/{id}")
    // public ResponseEntity<?> update(@PathVariable int id, @RequestParam(name =
    // "status") int status) {
    // TutorApply tutorApply =
    // tutorApplyService.getTutorApplyById(id).orElseThrow();
    // tutorApply.setStatus(status);

    // tutorApplyService.save(tutorApply);

    // return ResponseEntity.ok("Cập nhật thành công.");
    // }

    @PutMapping("/acceptTutor/{taId}")
    public ApiResponseDto<?> update(@PathVariable int taId) {
        TutorApply tutorApply = tutorApplyService.getTutorApplyById(taId).orElse(null);
        for (TutorApply ta : tutorApplyService.getTutorAppliesByClazzId(tutorApply.getClazz().getId())) {
            if (ta.getId() == taId)
                ta.setStatus(1);// accepted
            else
                ta.setStatus(2);// rejected
        }
        tutorApply.getClazz().setTutor(tutorApply.getTutor());

        tutorApplyService.save(tutorApply);

        return ApiResponseDto.<String>builder().data(tutorApply.getTutor().getFullname()).build();
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disable(@PathVariable int id) {
        TutorApply tutorApply = tutorApplyService.getTutorApplyById(id).orElseThrow();
        tutorApply.setDeleted(true);

        tutorApplyService.save(tutorApply);

        return ResponseEntity.ok("Disable thành công.");
    }

    // @PutMapping("/accept")
    // public ResponseEntity<?> acceptTutorApplied(
    // @RequestParam(name = "pId") int pId,
    // // @RequestParam(name = "tId") int tId,
    // @RequestParam(name = "taId") int taId) {

    // TutorApply tutorApply =
    // tutorApplyService.getTutorApplyById(taId).orElseThrow();

    // if (tutorApply.getClazz().getRequest().getParent().getId() != pId) {
    // return null;
    // }

    // for (TutorApply ta : tutorApplyService.getTutorAppliesByTutorId(pId)) {
    // if (ta.getId() == taId) {
    // update(ta.getId(), 1);// 1 = accepted
    // ta.getClazz().getTutor().setId(tutorApply.getTutor().getId());
    // clazzService.save(ta.getClazz());
    // } else {
    // update(ta.getId(), 2);// 2 = rejected
    // }
    // }

    // return ResponseEntity.ok("Chọn gia sư thành công.");
    // }
}
