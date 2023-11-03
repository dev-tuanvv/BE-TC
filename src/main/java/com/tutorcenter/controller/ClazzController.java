package com.tutorcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ClazzDto;
import com.tutorcenter.dto.PaginRes;
import com.tutorcenter.dto.clazz.SearchReqDto;
import com.tutorcenter.dto.clazz.SearchResDto;
import com.tutorcenter.model.Attendance;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Feedback;
import com.tutorcenter.model.Order;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.TutorApply;
import com.tutorcenter.service.AttendanceService;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.FeedbackService;
import com.tutorcenter.service.OrderService;
import com.tutorcenter.service.RequestService;
import com.tutorcenter.service.TutorApplyService;

@RestController
@RequestMapping("/api/Class")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TutorApplyService tutorApplyService;
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("")
    public List<Clazz> getAllClazzs() {
        return clazzService.findAll();
    }

    @GetMapping("/parent/{pId}")
    public List<Clazz> getClazzByParentId(@PathVariable int pId) {

        return clazzService.getClazzByParentId(pId);
    }

    @GetMapping("/manager/{mId}")
    public List<Clazz> getClazzByManagerId(@PathVariable int mId) {

        return clazzService.getClazzByManagerId(mId);
    }

    @GetMapping("/tutor/{tId}")
    public List<Clazz> getClazzByTutorId(@PathVariable int tId) {

        return clazzService.getClazzByTutorId(tId);
    }

    @PostMapping("/search")
    public PaginRes<SearchResDto> search(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc") String order,
            @RequestBody SearchReqDto searchDto) {

        List<SearchResDto> data = clazzService.search(limit, offset, searchDto, order);
        return PaginRes.<SearchResDto>builder().data(data).itemsPerPage(limit).page(offset).build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ClazzDto clazztDto, @RequestBody int rId) {
        Clazz clazz = new Clazz();
        clazztDto.convertClazzDto(clazz);
        Request request = requestService.getRequestById(rId).orElseThrow();
        Feedback feedback = null;
        List<Order> orders = null;
        List<TutorApply> tutorApplies = null;
        List<Attendance> attendances = null; // co the tao attendance luc tao class

        clazz.setRequest(request);
        clazz.setFeedback(feedback);
        clazz.setOrders(orders);
        clazz.setTutorApplies(tutorApplies);
        clazz.setAttendances(attendances);

        clazzService.save(clazz);

        return ResponseEntity.ok("Tạo lớp thành công.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @RequestBody ClazzDto clazzDto) {
        Clazz clazz = new Clazz();
        clazzDto.convertClazzDto(clazz);
        Request request = requestService.getRequestById(clazzDto.getRequestId()).orElseThrow();
        Feedback feedback = feedbackService.getFeedBackById(clazzDto.getFeedbackId()).orElseThrow();
        List<Order> orders = orderService.getOrdersById(clazzDto.getOrders());
        List<TutorApply> tutorApplies = tutorApplyService.getTutorAppliesById(clazzDto.getTutorApplies());
        List<Attendance> attendances = attendanceService.getAttendancesById(clazzDto.getAttendances());

        clazz.setRequest(request);
        clazz.setFeedback(feedback);
        clazz.setOrders(orders);
        clazz.setTutorApplies(tutorApplies);
        clazz.setAttendances(attendances);

        clazzService.save(clazz);

        return ResponseEntity.ok("Cập nhật thành công class.");
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Clazz> disableClazz(@PathVariable int id) {
        Clazz clazz = clazzService.getClazzById(id).orElseThrow();
        clazz.setDeleted(true);
        return ResponseEntity.ok(clazzService.save(clazz));
    }
}
