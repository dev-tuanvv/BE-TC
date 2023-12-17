package com.tutorcenter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.admin.MonthData;
import com.tutorcenter.dto.admin.YearlySaleResDto;
import com.tutorcenter.model.Order;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.OrderService;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClazzService clazzService;

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

    @Data
    class TotalSale {
        private float totalTuition;
        private float totalPaid;
        private float totalRevenue;
    }
}
