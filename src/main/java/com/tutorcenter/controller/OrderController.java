package com.tutorcenter.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorcenter.dto.ApiResponseDto;
import com.tutorcenter.dto.order.CreateOrderReqDto;
import com.tutorcenter.dto.order.CreateOrderResDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Order;
import com.tutorcenter.model.UserWallet;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.NotificationService;
import com.tutorcenter.service.OrderService;
import com.tutorcenter.service.SysWalletService;
import com.tutorcenter.service.UserService;
import com.tutorcenter.service.UserWalletService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private SysWalletService sysWalletService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/clazz/{cId}")
    public ApiResponseDto<List<CreateOrderResDto>> getOrdersByClazzId(@PathVariable int cId) {
        try {
            List<Order> orders = orderService.getOrdersByClazzId(cId);
            List<CreateOrderResDto> response = new ArrayList<>();
            if (orders.isEmpty())
                return ApiResponseDto.<List<CreateOrderResDto>>builder().responseCode("500")
                        .message("Chưa có order được tạo cho lớp này").build();
            for (Order o : orders) {
                CreateOrderResDto dto = new CreateOrderResDto();
                dto.fromOrder(o);
                response.add(dto);
            }
            return ApiResponseDto.<List<CreateOrderResDto>>builder().data(response).build();
        } catch (Exception e) {
            return ApiResponseDto.<List<CreateOrderResDto>>builder().responseCode("500").message(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/create")
    public ApiResponseDto<CreateOrderResDto> create(@RequestBody CreateOrderReqDto orderReqDto) {
        CreateOrderResDto dto = new CreateOrderResDto();
        try {

            Clazz clazz = clazzService.getClazzById(orderReqDto.getClazzId()).orElse(null);

            Order order = new Order();
            orderReqDto.toOrder(order);
            order.setClazz(clazz);
            order.setStatus(0);
            order.setTimeCreate(new Date(System.currentTimeMillis()));

            if (orderReqDto.getType() == 1) { // phụ huynh đóng tiền cho system
                order.setUser(userService.getUserById(clazz.getRequest().getParent().getId()).orElse(null));
                userWalletService.withdraw(clazz.getRequest().getParent().getId(), orderReqDto.getAmount());
                sysWalletService.deposit(orderReqDto.getAmount());
                // noti cho phụ huynh
                notificationService.add(order.getUser(),
                        "Đã đóng " + order.getAmount() + " cho lớp " + order.getClazz().getId() + " thành công");
                // noti cho manager
                notificationService.add(order.getClazz().getRequest().getManager(),
                        "Đã nhận " + order.getAmount() + " phí tạo lớp "
                                + order.getClazz().getId() + " từ phụ huynh " + order.getUser().getFullname());
            } else if (orderReqDto.getType() == 2) { // system trả tiền dạy cho gia sư
                order.setUser(userService.getUserById(clazz.getTutor().getId()).orElse(null));
                if (orderReqDto.getAmount() > sysWalletService.getBalance())
                    return ApiResponseDto.<CreateOrderResDto>builder()
                            .message("Số dư trong tài khoản hệ thống không đủ.")
                            .build();
                sysWalletService.withdraw(orderReqDto.getAmount());
                userWalletService.deposit(clazz.getTutor().getId(), orderReqDto.getAmount());
                // noti cho gia sư
                notificationService.add(order.getUser(),
                        "Đã nhận " + order.getAmount() + " học phí từ dạy lớp " + order.getClazz().getId()
                                + " thành công");
                // noti cho manager
                notificationService.add(order.getClazz().getRequest().getManager(),
                        "Đã chuyển" + order.getAmount() + " tiền cho gia sư "
                                + order.getUser().getFullname() + " từ dạy lớp " + order.getClazz().getId());
            }

            dto.fromOrder(orderService.save(order));
        } catch (Exception e) {
            return ApiResponseDto.<CreateOrderResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<CreateOrderResDto>builder().data(dto).build();
    }
}
