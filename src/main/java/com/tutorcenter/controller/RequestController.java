package com.tutorcenter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tutorcenter.dto.RequestDto;
import com.tutorcenter.dto.request.CreateRequestReqDto;
import com.tutorcenter.dto.request.ParentRequestResDto;
import com.tutorcenter.dto.request.RequestDetailResDto;
import com.tutorcenter.dto.request.RequestResDto;
import com.tutorcenter.dto.subject.SubjectLevelResDto;
import com.tutorcenter.model.District;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Parent;
import com.tutorcenter.model.Request;
import com.tutorcenter.model.Subject;
import com.tutorcenter.model.Task;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.DistrictService;
import com.tutorcenter.service.EmailService;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.NotificationService;
import com.tutorcenter.service.ParentService;
import com.tutorcenter.service.RequestService;
import com.tutorcenter.service.RequestSubjectService;
import com.tutorcenter.service.SubjectService;
import com.tutorcenter.service.TaskService;

@RestController
// @PreAuthorize("hasAnyAuthority('admin:read')")
@RequestMapping("/api/request")
public class RequestController {
    @Autowired
    private RequestService requestService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private RequestSubjectService requestSubjectService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EmailService emailService;

    @GetMapping("")
    public ApiResponseDto<List<RequestResDto>> getListRequest() {

        List<RequestResDto> response = new ArrayList<>();
        try {

            for (Request request : requestService.findAll()) {
                RequestResDto dto = new RequestResDto();
                dto.fromRequest(request);
                // Tạo list SubjectLevel
                List<Integer> listSId = requestSubjectService
                        .getListSIdByRId(request.getId());
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
            return ApiResponseDto.<List<RequestResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<RequestResDto>>builder().data(response).build();
    }

    // @PreAuthorize("hasAnyAuthority('admin:readd')")
    // này test trường hợp k author được, vì admin:readd chứ k phải admin:read
    @GetMapping("/{id}")
    public ApiResponseDto<RequestDetailResDto> getRequestDetailById(@PathVariable(value = "id") int id) {
        RequestDetailResDto response = new RequestDetailResDto();
        try {

            Request request = requestService.getRequestById(id).orElse(null);
            if (request == null) {
                return ApiResponseDto.<RequestDetailResDto>builder().responseCode("404").message("Request not found")
                        .build();
            }

            response.fromRequest(request);
            List<Integer> listSId = requestSubjectService
                    .getListSIdByRId(id);
            List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

            List<SubjectLevelResDto> listSL = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectLevelResDto sLDto = new SubjectLevelResDto();
                sLDto.fromSubject(subject);
                listSL.add(sLDto);
            }
            response.setSubjects(listSL);

        } catch (Exception e) {
            return ApiResponseDto.<RequestDetailResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<RequestDetailResDto>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('parent:read')")
    @GetMapping("/parent/{pId}")
    public ApiResponseDto<List<ParentRequestResDto>> getRequestByParentId() {
        List<ParentRequestResDto> response = new ArrayList<>();
        try {
            int pId = Common.getCurrentUserId();
            Parent parent = parentService.getParentById(pId).orElse(null);
            if (parent == null) {
                return ApiResponseDto.<List<ParentRequestResDto>>builder().responseCode("404")
                        .message("Parent not found")
                        .build();
            }

            List<Request> listRequests = requestService.getRequestByParentID(pId);

            if (listRequests == null || listRequests.isEmpty()) {
                return ApiResponseDto.<List<ParentRequestResDto>>builder().responseCode("404")
                        .message("Parent don't have any request")
                        .build();
            }
            for (Request r : listRequests) {
                ParentRequestResDto dto = new ParentRequestResDto();
                dto.fromRequest(r);
                // Tạo list SubjectLevel từ requestId
                List<Integer> listSId = requestSubjectService
                        .getListSIdByRId(r.getId());
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
            return ApiResponseDto.<List<ParentRequestResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
        return ApiResponseDto.<List<ParentRequestResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('manager:read')")
    @GetMapping("/manager")
    public ApiResponseDto<List<RequestResDto>> getRequestByManagerId() {
        List<RequestResDto> response = new ArrayList<>();
        try {
            int mId = Common.getCurrentUserId();
            Manager manager = managerService.getManagerById(mId).orElse(null);
            if (manager == null || mId != Common.getCurrentUserId()) {
                return ApiResponseDto.<List<RequestResDto>>builder().responseCode("404").message("Manager not found")
                        .build();
            }

            List<Request> listRequests = requestService.getRequestByManagerID(mId);
            if (listRequests == null || listRequests.isEmpty()) {
                return ApiResponseDto.<List<RequestResDto>>builder().responseCode("404")
                        .message("Manager don't have any request")
                        .build();
            }
            for (Request request : listRequests) {
                RequestResDto requestResDto = new RequestResDto();
                requestResDto.fromRequest(request);
                // tạo list SubjectLevel từ requestId
                List<Integer> listSId = requestSubjectService
                        .getListSIdByRId(request.getId());
                List<Subject> subjects = subjectService.getSubjectsByListId(listSId);

                List<SubjectLevelResDto> listSL = new ArrayList<>();
                for (Subject subject : subjects) {
                    SubjectLevelResDto sLDto = new SubjectLevelResDto();
                    sLDto.fromSubject(subject);
                    listSL.add(sLDto);
                }

                requestResDto.setSubjects(listSL);
                response.add(requestResDto);
            }
        } catch (Exception e) {
            return ApiResponseDto.<List<RequestResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<List<RequestResDto>>builder().data(response).build();
    }

    @PreAuthorize("hasAnyAuthority('parent:create')")
    @PostMapping("/create")
    public ApiResponseDto<Integer> createRequest(
            @RequestBody CreateRequestReqDto createRequestDto) {
        try {
            Request request = new Request();
            // TODO: lay Id tu Session
            createRequestDto.toRequest(request);
            Manager manager = managerService.getManagerById(taskService.findBestSuitManagerId()).orElse(null);
            // Manager manager = managerService.getManagerById(3).orElse(null);
            request.setManager(manager);
            request.setParent(parentService.getParentById(Common.getCurrentUserId()).orElse(null));
            District district = districtService.getDistrictById(createRequestDto.getDistrictId()).orElse(null);
            if (district == null) {
                return ApiResponseDto.<Integer>builder().responseCode("404").message("District not found").build();
            }
            request.setDistrict(district);
            Request response = requestService.save(request);
            requestSubjectService.updateByRequestId(response.getId(), createRequestDto.getListSubjectId());

            Task task = new Task();
            task.setManager(manager);
            task.setName("Request");
            task.setType(1);
            task.setStatus(1);
            task.setDateCreate(new Date(System.currentTimeMillis()));
            task.setRequestId(response.getId());
            taskService.save(task);
            notificationService.add(request.getParent(), "Tạo yêu cầu tìm gia sư thành công");
            emailService.sendEmail(manager.getEmail(), "Task mới đã được assign",
                    "Bạn đã được assign  task: " + task.getId());
            return ApiResponseDto.<Integer>builder().message(null).data(response.getId()).build();
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    // 1=accept or 2=reject
    @PreAuthorize("hasAnyAuthority('manager:update')")
    @PutMapping("/updateStatus")
    public ApiResponseDto<Integer> updateSubjects(@RequestParam(name = "requestId") int rId,
            @RequestParam(name = "status") int status, @RequestParam(name = "rejectReason") String rr) {
        try {

            Request rq = requestService.getRequestById(rId).orElse(null);
            if (rq == null) {
                return ApiResponseDto.<Integer>builder().data(null).responseCode("404").message("Not found").build();
            }
            rq.setStatus(status); // 1 or 2
            rq.setRejectReason(rr);

            requestService.save(rq);
            notificationService.add(rq.getParent(), "Yêu cầu tìm gia sư đã được xét duyệt");
            // finish task
            taskService.finish(rId, 1);

        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }

        return ApiResponseDto.<Integer>builder().data(rId).build();
    }

    //
    @PutMapping("/update/{id}")
    public ApiResponseDto<Request> updateRequest(
            @PathVariable(value = "id") int id,
            @RequestBody RequestDto requestDto) {
        try {

            Request rq = requestService.getRequestById(id).orElse(null);
            if (rq == null) {
                return ApiResponseDto.<Request>builder().data(null).responseCode("404").message("Not found").build();
            }
            requestDto.convertRequestDto(rq);
            rq.setParent(parentService.getParentById(requestDto.getParentId()).orElse(null));
            rq.setManager(managerService.getManagerById(requestDto.getManagerId()).orElse(null));
            rq.setClazz(clazzService.getClazzById(requestDto.getClazzId()).orElse(null));
            rq.setDistrict(districtService.getDistrictById(requestDto.getDistrictId()).orElse(null));

            notificationService.add(rq.getParent(), "Yêu cầu tìm gia sư đã được cập nhật");
            return ApiResponseDto.<Request>builder().data(requestService.save(rq)).build();
        } catch (Exception e) {
            return ApiResponseDto.<Request>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('parent:delete')")
    @DeleteMapping("/delete/{id}")
    public ApiResponseDto<Integer> disableRequest(@PathVariable int id) {
        try {

            Request rq = requestService.getRequestById(id).orElseThrow();
            if (rq.getParent().getId() == Common.getCurrentUserId()) {
                rq.setDeleted(true);
                notificationService.add(rq.getParent(), "Yêu cầu tìm gia sư đã được xóa");
                requestService.save(rq);
            }
            return ApiResponseDto.<Integer>builder().data(id).build();
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('parent:delete')")
    @DeleteMapping("/cancel/{id}")
    public ApiResponseDto<Integer> cancelRequest(@PathVariable int id) {
        try {

            Request rq = requestService.getRequestById(id).orElseThrow();
            if (rq.getParent().getId() == Common.getCurrentUserId() && rq.getStatus() == 0) {
                rq.setStatus(3);
                notificationService.add(rq.getParent(), "Yêu cầu tìm gia sư đã được hủy bỏ");
                requestService.save(rq);
                for (Task t : taskService.getAllTask()) {
                    if (t.getType() == 1 && t.getRequestId() == id && t.getStatus() == 0) {
                        t.setType(5); // cancel request > task disable
                        taskService.save(t);
                    }
                }
            }
            return ApiResponseDto.<Integer>builder().data(id).build();
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
    }
}
