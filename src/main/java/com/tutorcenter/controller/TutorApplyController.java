package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.model.Clazz;
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
    public List<TutorApply> getTutorAppliesByClazzId(@PathVariable int id) {
        return tutorApplyService.getTutorAppliesByClazzId(id);
    }

    @GetMapping("/tutor/{id}")
    public List<TutorApply> getTutorAppliesByTutorId(@PathVariable int id) {
        return tutorApplyService.getTutorAppliesByTutorId(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam(name = "clazzId") int cId, @RequestParam(name = "tutorId") int tId) {
        TutorApply tutorApply = new TutorApply();
        Clazz clazz = clazzService.getClazzById(cId).orElseThrow();
        Tutor tutor = tutorService.getTutorById(tId).orElseThrow();

        tutorApply.setClazz(clazz);
        tutorApply.setTutor(tutor);
        tutorApply.setDeleted(false);
        tutorApply.setStatus(0);

        tutorApplyService.save(tutorApply);

        return ResponseEntity.ok("Apply thành công.");
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestParam(name = "status") int status) {
        TutorApply tutorApply = tutorApplyService.getTutorApplyById(id).orElseThrow();
        tutorApply.setStatus(status);

        tutorApplyService.save(tutorApply);

        return ResponseEntity.ok("Cập nhật thành công.");
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disable(@PathVariable int id) {
        TutorApply tutorApply = tutorApplyService.getTutorApplyById(id).orElseThrow();
        tutorApply.setDeleted(true);

        tutorApplyService.save(tutorApply);

        return ResponseEntity.ok("Disable thành công.");
    }

    @PutMapping("/accept")
    public ResponseEntity<?> acceptTutorApplied(
            @RequestParam(name = "pId") int pId,
            // @RequestParam(name = "tId") int tId,
            @RequestParam(name = "taId") int taId) {

        TutorApply tutorApply = tutorApplyService.getTutorApplyById(taId).orElseThrow();

        if (tutorApply.getClazz().getRequest().getParent().getId() != pId) {
            return null;
        }

        for (TutorApply ta : tutorApplyService.getTutorAppliesByTutorId(pId)) {
            if (ta.getId() == taId) {
                update(ta.getId(), 1);// 1 = accepted
                ta.getClazz().getTutor().setId(tutorApply.getTutor().getId());
                clazzService.save(ta.getClazz());
            } else {
                update(ta.getId(), 2);// 2 = rejected
            }
        }

        return ResponseEntity.ok("Chọn gia sư thành công.");
    }
}
