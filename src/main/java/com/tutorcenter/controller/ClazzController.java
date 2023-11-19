package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.PaginRes;
import com.tutorcenter.dto.clazz.ClazzDetailResDto;
import com.tutorcenter.dto.clazz.CreateClazzResDto;
import com.tutorcenter.dto.clazz.ListClazzResDto;
import com.tutorcenter.dto.clazz.SearchReqDto;
import com.tutorcenter.dto.clazz.SearchResDto;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Attendance;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Feedback;
import com.tutorcenter.model.Order;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.Subject;
import com.tutorcenter.model.Tutor;
import com.tutorcenter.model.TutorApply;
import com.tutorcenter.service.AttendanceService;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.FeedbackService;
import com.tutorcenter.service.OrderService;
import com.tutorcenter.service.RequestService;
import com.tutorcenter.service.RequestSubjectService;
import com.tutorcenter.service.SubjectService;
import com.tutorcenter.service.TutorApplyService;
import com.tutorcenter.service.TutorService;

@RestController
@RequestMapping("/api/clazz")
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
    @Autowired
    private TutorService tutorService;
    @Autowired
    private RequestSubjectService requestSubjectService;
    @Autowired
    SubjectService subjectService;

    @GetMapping("")
    public ApiResponseDto<List<ListClazzResDto>> getAllClazzs() {

        List<Clazz> clazzs = clazzService.findAll();
        List<ListClazzResDto> response = new ArrayList<>();
        for (Clazz c : clazzs) {
            ListClazzResDto dto = new ListClazzResDto();
            dto.fromClazz(c);
            // Tạo list SubjectLevel từ requestId
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(c.getRequest().getId());
            List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

            List<SubjectLevelResDto> listSL = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectLevelResDto sLDto = new SubjectLevelResDto();
                sLDto.fromSubject(subject);
                listSL.add(sLDto);
            }

            dto.setSubjects(listSL);
            response.add(dto);
        }

        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/{id}")
    public ApiResponseDto<ClazzDetailResDto> getClazzDetail(@PathVariable int id) {
        Clazz clazz = clazzService.getClazzById(id).orElse(null);
        ClazzDetailResDto dto = new ClazzDetailResDto();
        dto.fromClazz(clazz);
        // Tạo list SubjectLevel từ requestId
        List<Integer> listSId = requestSubjectService
                .getListSIdByRId(clazz.getRequest().getId());
        List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

        List<SubjectLevelResDto> listSL = new ArrayList<>();
        for (Subject subject : subjects) {
            SubjectLevelResDto sLDto = new SubjectLevelResDto();
            sLDto.fromSubject(subject);
            listSL.add(sLDto);
        }
        // set list SubjectLevel vào subjectDto
        dto.setSubjects(listSL);

        return ApiResponseDto.<ClazzDetailResDto>builder().data(dto).build();
    }

    @GetMapping("/subject/{sId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzBySubjectId(@PathVariable int sId) {
        List<Clazz> clazzs = clazzService.findAll();
        List<ListClazzResDto> response = new ArrayList<>();
        for (Clazz c : clazzs) {
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(c.getRequest().getId());

            for (int i : listSId) {
                if (i == sId) {
                    // tạo subjectDto
                    ListClazzResDto dto = new ListClazzResDto();
                    dto.fromClazz(c);
                    // tạo SubjectLevel từ requestId
                    List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

                    List<SubjectLevelResDto> listSL = new ArrayList<>();
                    for (Subject subject : subjects) {
                        SubjectLevelResDto sLDto = new SubjectLevelResDto();
                        sLDto.fromSubject(subject);
                        listSL.add(sLDto);
                    }
                    // set list SubjectLevel
                    dto.setSubjects(listSL);
                    response.add(dto);
                }
            }

        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/level/{lId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByLevel(@PathVariable String level) {
        List<Clazz> clazzs = clazzService.findAll();
        List<ListClazzResDto> response = new ArrayList<>();
        for (Clazz c : clazzs) {
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(c.getRequest().getId());
            List<Subject> subjects = subjectService.getSubjectsByListId(listSId);
            for (Subject s : subjects) {
                if (s.getLevel().equalsIgnoreCase(level)) {
                    // tạo subjectDto
                    ListClazzResDto dto = new ListClazzResDto();
                    dto.fromClazz(c);
                    // tạo SubjectLevel từ requestId

                    List<SubjectLevelResDto> listSL = new ArrayList<>();
                    for (Subject subject : subjects) {
                        SubjectLevelResDto sLDto = new SubjectLevelResDto();
                        sLDto.fromSubject(subject);
                        listSL.add(sLDto);
                    }
                    // set list SubjectLevel
                    dto.setSubjects(listSL);
                    response.add(dto);
                }
            }

        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/district/{dId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByDistrictId(@PathVariable int dId) {
        List<Clazz> clazzs = clazzService.getClazzByDistrict(dId);
        List<ListClazzResDto> response = new ArrayList<>();
        for (Clazz c : clazzs) {
            ListClazzResDto dto = new ListClazzResDto();
            dto.fromClazz(c);
            // Tạo list SubjectLevel từ requestId
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(c.getRequest().getId());
            List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

            List<SubjectLevelResDto> listSL = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectLevelResDto sLDto = new SubjectLevelResDto();
                sLDto.fromSubject(subject);
                listSL.add(sLDto);
            }

            dto.setSubjects(listSL);
            response.add(dto);
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('parent:read')")
    @GetMapping("/parent/{pId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByParentId(@PathVariable int pId) {
        List<Clazz> clazzs = clazzService.getClazzByParentId(pId);
        List<ListClazzResDto> response = new ArrayList<>();
        for (Clazz c : clazzs) {
            ListClazzResDto dto = new ListClazzResDto();
            dto.fromClazz(c);
            if (c.getTutor() != null) {
                dto.fromTutor(c.getTutor());
            }
            // Tạo list SubjectLevel từ requestId
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(c.getRequest().getId());
            List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

            List<SubjectLevelResDto> listSL = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectLevelResDto sLDto = new SubjectLevelResDto();
                sLDto.fromSubject(subject);
                listSL.add(sLDto);
            }

            dto.setSubjects(listSL);
            response.add(dto);
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('manager:read')")
    @GetMapping("/manager/{mId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByManagerId(@PathVariable int mId) {
        List<Clazz> clazzs = clazzService.getClazzByManagerId(mId);
        List<ListClazzResDto> response = new ArrayList<>();
        for (Clazz c : clazzs) {
            ListClazzResDto dto = new ListClazzResDto();
            dto.fromClazz(c);
            // Tạo list SubjectLevel từ requestId
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(c.getRequest().getId());
            List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

            List<SubjectLevelResDto> listSL = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectLevelResDto sLDto = new SubjectLevelResDto();
                sLDto.fromSubject(subject);
                listSL.add(sLDto);
            }

            dto.setSubjects(listSL);
            response.add(dto);
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/tutor/{tId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByTutorId(@PathVariable int tId) {
        List<Clazz> clazzs = clazzService.getClazzByTutorId(tId);
        List<ListClazzResDto> response = new ArrayList<>();
        for (Clazz c : clazzs) {
            ListClazzResDto dto = new ListClazzResDto();
            dto.fromClazz(c);
            // Tạo list SubjectLevel từ requestId
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(c.getRequest().getId());
            List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

            List<SubjectLevelResDto> listSL = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectLevelResDto sLDto = new SubjectLevelResDto();
                sLDto.fromSubject(subject);
                listSL.add(sLDto);
            }

            dto.setSubjects(listSL);
            response.add(dto);
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/status/{status}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByStatus(@PathVariable int status) {
        List<Clazz> clazzs = clazzService.getClazzByStatus(status);
        List<ListClazzResDto> response = new ArrayList<>();
        for (Clazz c : clazzs) {
            ListClazzResDto dto = new ListClazzResDto();
            dto.fromClazz(c);
            // Tạo list SubjectLevel từ requestId
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(c.getRequest().getId());
            List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

            List<SubjectLevelResDto> listSL = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectLevelResDto sLDto = new SubjectLevelResDto();
                sLDto.fromSubject(subject);
                listSL.add(sLDto);
            }

            dto.setSubjects(listSL);
            response.add(dto);
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }
    // @GetMapping("/subject/{sId}")
    // public ApiResponseDto<List<CreateClazzResDto>>
    // getClazzsBySubjectId(@PathVariable int sId) {
    // List<CreateClazzResDto> response = new ArrayList<>();
    // for (Clazz clazz : clazzService.findAll()) {
    // List<RequestSubject> rsList =
    // requestSubjectService.getRSubjectByRId(clazz.getRequest().getId());
    // for (RequestSubject rs : rsList) {
    // if (rs.getSubject().getId() == sId) {
    // CreateClazzResDto dto = new CreateClazzResDto();
    // dto.convertClazz(clazz);
    // response.add(dto);
    // }
    // }
    // }

    // return
    // ApiResponseDto.<List<CreateClazzResDto>>builder().data(response).build();
    // }

    @PostMapping("/search")
    public PaginRes<SearchResDto> search(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "desc") String order,
            @RequestBody SearchReqDto searchDto) {

        List<SearchResDto> data = clazzService.search(limit, offset, searchDto, order);
        return PaginRes.<SearchResDto>builder().data(data).itemsPerPage(limit).page(offset).build();
    }

    @PreAuthorize("hasAnyAuthority('manager:create')")
    @PostMapping("/create")
    public ApiResponseDto<Integer> create(@RequestParam(name = "requestId") int rId) {
        Clazz clazz = new Clazz();

        Request request = requestService.getRequestById(rId).orElse(null);
        Feedback feedback = null;
        Tutor tutor = null;
        List<Order> orders = null;
        List<TutorApply> tutorApplies = null;
        List<Attendance> attendances = null; // co the tao attendance luc tao class

        clazz.setRequest(request);
        clazz.setFeedback(feedback);
        clazz.setTutor(tutor);
        clazz.setOrders(orders);
        clazz.setTutorApplies(tutorApplies);
        clazz.setAttendances(attendances);
        clazz.setStatus(0);
        clazz.setDeleted(false);

        int clazzId = clazzService.save(clazz).getId();

        return ApiResponseDto.<Integer>builder().data(clazzId).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @RequestBody CreateClazzResDto clazzDto) {
        Clazz clazz = new Clazz();
        clazzDto.convertClazzDto(clazz);
        // Request request =
        // requestService.getRequestById(clazzDto.getRequestId()).orElseThrow();
        // Feedback feedback =
        // feedbackService.getFeedBackById(clazzDto.getFeedbackId()).orElseThrow();
        // Tutor tutor = tutorService.getTutorById(clazzDto.getTutorId()).orElseThrow();
        // List<Order> orders = orderService.getOrdersById(clazzDto.getOrders());
        // List<TutorApply> tutorApplies =
        // tutorApplyService.getTutorAppliesById(clazzDto.getTutorApplies());
        // List<Attendance> attendances =
        // attendanceService.getAttendancesById(clazzDto.getAttendances());

        // clazz.setRequest(request);
        // clazz.setFeedback(feedback);
        // clazz.setTutor(tutor);
        // clazz.setOrders(orders);
        // clazz.setTutorApplies(tutorApplies);
        // clazz.setAttendances(attendances);

        clazzService.save(clazz);

        return ResponseEntity.ok("Cập nhật thành công class.");
    }

    @PreAuthorize("hasAnyAuthority('manager:update')")
    @PutMapping("/updateStatus")
    public ApiResponseDto<Integer> updateClazzStatus(@RequestParam(name = "clazzId") int id,
            @RequestParam(name = "status") int status) {
        Clazz clazz = clazzService.getClazzById(id).orElseThrow();
        clazz.setStatus(status);
        return ApiResponseDto.<Integer>builder().data(clazzService.save(clazz).getId()).build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponseDto<Integer> disableClazz(@PathVariable int id) {
        Clazz clazz = clazzService.getClazzById(id).orElseThrow();
        clazz.setDeleted(true);
        return ApiResponseDto.<Integer>builder().data(clazzService.save(clazz).getId()).build();
    }
}
