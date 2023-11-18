package com.tutorcenter.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.order.CreateOrderReqDto;
import com.tutorcenter.dto.order.CreateOrderResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Order;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.OrderService;
import com.tutorcenter.service.UserService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ApiResponseDto<CreateOrderResDto> create(@RequestBody CreateOrderReqDto orderReqDto) {
        Clazz clazz = clazzService.getClazzById(orderReqDto.getClazzId()).orElse(null);

        Order order = new Order();
        orderReqDto.toOrder(order);
        order.setClazz(clazz);
        order.setStatus(0);
        order.setTimeCreate(new Date(System.currentTimeMillis()));

        if (orderReqDto.getType() == 1)
            order.setUser(userService.getUserById(clazz.getRequest().getParent().getId()).orElse(null));
        else if (orderReqDto.getType() == 2)
            order.setUser(userService.getUserById(clazz.getTutor().getId()).orElse(null));

        CreateOrderResDto dto = new CreateOrderResDto();

        dto.fromOrder(orderService.save(order));

        return ApiResponseDto.<CreateOrderResDto>builder().data(dto).build();
    }
}
