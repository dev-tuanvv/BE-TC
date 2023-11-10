package com.tutorcenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.order.CreateOrderReqDto;
import com.tutorcenter.model.Order;
import com.tutorcenter.service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ApiResponseDto<Integer> create(@RequestBody CreateOrderReqDto orderReqDto) {
        Order order = new Order();
        orderReqDto.toOrder(order);
        int oId = orderService.save(order).getId();

        return ApiResponseDto.<Integer>builder().data(oId).build();
    }
}
