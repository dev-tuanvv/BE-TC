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

import com.tutorcenter.common.Common;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.PaginRes;
import com.tutorcenter.dto.clazz.ClazzDetailResDto;
import com.tutorcenter.dto.clazz.CreateClazzResDto;
import com.tutorcenter.dto.clazz.ListClazzByTutorResDto;
import com.tutorcenter.dto.clazz.ListClazzResDto;
import com.tutorcenter.dto.clazz.SearchReqDto;
import com.tutorcenter.dto.clazz.SearchResDto;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.Subject;
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
        List<ListClazzResDto> response = new ArrayList<>();
        try {
            List<Clazz> clazzs = clazzService.findAll();

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
        } catch (Exception e) {
            return ApiResponseDto.<List<ListClazzResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/{id}")
    public ApiResponseDto<ClazzDetailResDto> getClazzDetail(@PathVariable int id) {
        ClazzDetailResDto dto = new ClazzDetailResDto();
        try {
            Clazz clazz = clazzService.getClazzById(id).orElse(null);

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
        } catch (Exception e) {
            return ApiResponseDto.<ClazzDetailResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<ClazzDetailResDto>builder().data(dto).build();
    }

    @GetMapping("/subject/{sId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzBySubjectId(@PathVariable int sId) {
        List<ListClazzResDto> response = new ArrayList<>();
        try {

            List<Clazz> clazzs = clazzService.findAll();

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
        } catch (Exception e) {
            return ApiResponseDto.<List<ListClazzResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/level/{lId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByLevel(@PathVariable String level) {
        List<ListClazzResDto> response = new ArrayList<>();
        try {

            List<Clazz> clazzs = clazzService.findAll();

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
        } catch (Exception e) {
            return ApiResponseDto.<List<ListClazzResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/district/{dId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByDistrictId(@PathVariable int dId) {
        List<ListClazzResDto> response = new ArrayList<>();
        try {

            List<Clazz> clazzs = clazzService.getClazzByDistrict(dId);

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
        } catch (Exception e) {
            return ApiResponseDto.<List<ListClazzResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('parent:read')")
    @GetMapping("/parent")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByParentId() {
        List<ListClazzResDto> response = new ArrayList<>();
        try {

            List<Clazz> clazzs = clazzService.getClazzByParentId(Common.getCurrentUserId());

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
        } catch (Exception e) {
            return ApiResponseDto.<List<ListClazzResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('tutor:read')")
    @GetMapping("/tutor")
    public ApiResponseDto<List<ListClazzByTutorResDto>> getClazzByTutorId() {
        List<ListClazzByTutorResDto> response = new ArrayList<>();
        try {

            List<Clazz> clazzs = clazzService.getClazzByTutorId(Common.getCurrentUserId());

            for (Clazz c : clazzs) {
                ListClazzByTutorResDto dto = new ListClazzByTutorResDto();
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
        } catch (Exception e) {
            return ApiResponseDto.<List<ListClazzByTutorResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
        return ApiResponseDto.<List<ListClazzByTutorResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('manager:read')")
    @GetMapping("/manager/{mId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByManagerId(@PathVariable int mId) {
        List<ListClazzResDto> response = new ArrayList<>();
        try {

            List<Clazz> clazzs = clazzService.getClazzByManagerId(mId);

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
        } catch (Exception e) {
            return ApiResponseDto.<List<ListClazzResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/tutor/{tId}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByTutorId(@PathVariable int tId) {
        List<ListClazzResDto> response = new ArrayList<>();
        try {

            List<Clazz> clazzs = clazzService.getClazzByTutorId(tId);

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

                    dto.setSubjects(listSL);
                    response.add(dto);
                }
            }
        } catch (Exception e) {
            return ApiResponseDto.<List<ListClazzResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }

        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
    }

    @GetMapping("/status/{status}")
    public ApiResponseDto<List<ListClazzResDto>> getClazzByStatus(@PathVariable int status) {
        List<ListClazzResDto> response = new ArrayList<>();
        try {

            List<Clazz> clazzs = clazzService.getClazzByStatus(status);

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
        } catch (Exception e) {
            return ApiResponseDto.<List<ListClazzResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<ListClazzResDto>>builder().data(response).build();
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

    // @PreAuthorize("hasAnyAuthority('manager:create')")
    @PostMapping("/create")
    public ApiResponseDto<Clazz> create(@RequestParam(name = "requestId") int rId) {
        Clazz clazz = new Clazz();
        try {

            Request request = requestService.getRequestById(rId).orElse(null);
            clazz.setRequest(request);
            clazz.setStatus(0);
            clazz.setDeleted(false);

            clazzService.save(clazz);
        } catch (Exception e) {
            return ApiResponseDto.<Clazz>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<Clazz>builder().data(clazz).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") int id, @RequestBody CreateClazzResDto clazzDto) {
        Clazz clazz = new Clazz();
        clazzDto.convertClazzDto(clazz);

        clazzService.save(clazz);

        return ResponseEntity.ok("Cập nhật thành công class.");
    }

    @PreAuthorize("hasAnyAuthority('manager:update')")
    @PutMapping("/updateStatus")
    public ApiResponseDto<Integer> updateClazzStatus(@RequestParam(name = "clazzId") int id,
            @RequestParam(name = "status") int status) {
        Clazz clazz = new Clazz();
        try {

            clazz = clazzService.getClazzById(id).orElseThrow();
            clazz.setStatus(status);
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<Integer>builder().data(clazzService.save(clazz).getId()).build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponseDto<Integer> disableClazz(@PathVariable int id) {
        Clazz clazz = new Clazz();
        try {

            clazz = clazzService.getClazzById(id).orElseThrow();
            clazz.setDeleted(true);
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<Integer>builder().data(clazzService.save(clazz).getId()).build();
    }
}
