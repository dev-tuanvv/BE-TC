package com.tutorcenter.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.constant.Role;
import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.admin.AddManagerReqDto;
import com.tutorcenter.dto.admin.ManagerResDto;
import com.tutorcenter.dto.admin.MonthData;
import com.tutorcenter.dto.admin.UpdateManagerReqDto;
import com.tutorcenter.dto.admin.YearlySaleResDto;
import com.tutorcenter.model.Manager;
import com.tutorcenter.model.Order;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.ManagerService;
import com.tutorcenter.service.OrderService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private ManagerService managerService;

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @GetMapping("/yearlySale")
    public ApiResponseDto<List<YearlySaleResDto>> getYearlySale() {
        try {
            List<YearlySaleResDto> response = new ArrayList<>();
            YearlySaleResDto twentythree = new YearlySaleResDto();
            Map<String, MonthData> map = new HashMap<>();
            List<Order> orders = orderService.findAll();
            SimpleDateFormat monthYearFormat = new SimpleDateFormat("MM-yyyy");

            for (Order order : orders) {
                String monthKey = monthYearFormat.format(order.getTimeCreate());
                map.putIfAbsent(monthKey, new MonthData());
                MonthData mData = map.get(monthKey);
                if (order.getType() == 1) {
                    float t = mData.getTotalTuition() + order.getAmount();
                    mData.setTotalTuition(t);
                } else if (order.getType() == 2) {
                    float t = mData.getTotalPaid() + order.getAmount();
                    mData.setTotalPaid(t);
                }
                map.put(monthKey, mData);
            }
            twentythree.setYear(2023);
            twentythree.setData(map);
            response.add(twentythree);
            return ApiResponseDto.<List<YearlySaleResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<YearlySaleResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @GetMapping("/totalSale")
    public ApiResponseDto<TotalSale> getTotalSale() {
        try {
            TotalSale response = new TotalSale();
            List<Order> orders = orderService.findAll();
            for (Order order : orders) {
                if (order.getType() == 1) {
                    response.totalTuition += order.getAmount();
                } else if (order.getType() == 2) {
                    response.totalPaid += order.getAmount();
                    response.totalRevenue += (response.totalTuition - response.totalPaid);
                }
            }
            // response.totalRevenue = response.totalTuition - response.totalPaid;
            return ApiResponseDto.<TotalSale>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<TotalSale>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @GetMapping("/ongoingClazz")
    public ApiResponseDto<Integer> getOngoingClazz() {
        try {
            int response = 0;
            response = clazzService.getClazzByStatus(1).size();
            return ApiResponseDto.<Integer>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<Integer>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @GetMapping("/list-managers")
    public ApiResponseDto<List<ManagerResDto>> getAllManagers() {
        try {
            List<ManagerResDto> response = new ArrayList<>();
            for (Manager manager : managerService.findAll()) {
                ManagerResDto dto = new ManagerResDto();
                dto.fromManager(manager);
                response.add(dto);
            }
            return ApiResponseDto.<List<ManagerResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<ManagerResDto>>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @GetMapping("/manager/{id}")
    public ApiResponseDto<ManagerResDto> getManagerById(@PathVariable int id) {
        try {
            ManagerResDto resDto = new ManagerResDto();
            Manager model = managerService.getManagerById(id).orElse(null);
            if (model == null) {
                return ApiResponseDto.<ManagerResDto>builder().message("Not found").build();
            } else {
                resDto.fromManager(model);
                return ApiResponseDto.<ManagerResDto>builder().data(resDto).build();
            }
        } catch (Exception e) {
            return ApiResponseDto.<ManagerResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @PutMapping("/manager/{id}")
    public ApiResponseDto updateManagerById(@PathVariable int id,
            @RequestBody UpdateManagerReqDto request) {
        try {
            Manager model = managerService.getManagerById(id).orElse(null);

            if (model == null) {
                return ApiResponseDto.builder().message("Not found").build();
            } else {
                model.setFullname(request.getFullname());
                model.setEmail(request.getEmail());
                model.setPhone(request.getPhone());
                model.setStatus(request.getStatus());
                managerService.save(model);
                return ApiResponseDto.builder().build();
            }
        } catch (Exception e) {
            return ApiResponseDto.builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @PutMapping("/manager/update-pass/{id}")
    public ApiResponseDto updatePasswordManagerById(@PathVariable int id,
            @RequestBody String request) {
        try {
            Manager model = managerService.getManagerById(id).orElse(null);

            if (model == null) {
                return ApiResponseDto.builder().message("Not found").build();
            } else {
                model.setPassword(passwordEncoder.encode(request));
                managerService.save(model);
                return ApiResponseDto.builder().build();
            }
        } catch (Exception e) {
            return ApiResponseDto.builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin:read')")
    @PostMapping("/manager")
    public ApiResponseDto<Manager> addManager(@RequestBody AddManagerReqDto request) {
        try {
            Manager model = new Manager();
            model.setFullname(request.getFullname());
            model.setEmail(request.getEmail());
            model.setPhone(request.getPhone());
            model.setPassword(passwordEncoder.encode(request.getPassword()));
            model.setRole(Role.MANAGER);
            model.setStatus(1);
            return ApiResponseDto.<Manager>builder().data(managerService.save(model)).build();

        } catch (Exception e) {
            return ApiResponseDto.<Manager>builder().responseCode("500").message(e.getMessage()).build();
        }
    }

    @Data
    class TotalSale {
        private float totalTuition;
        private float totalPaid;
        private float totalRevenue;
    }
}
