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
import com.tutorcenter.dto.order.CreatePartlyOrderReqDto;
import com.tutorcenter.model.Clazz;
import com.tutorcenter.model.Order;
import com.tutorcenter.model.UserWallet;
import com.tutorcenter.service.ClazzService;
import com.tutorcenter.service.NotificationService;
import com.tutorcenter.service.OrderService;
import com.tutorcenter.service.SysWalletService;
import com.tutorcenter.service.SystemVariableService;
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
    @Autowired
    private SystemVariableService systemVariableService;

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
            order.setTimeCreate(new Date(System.currentTimeMillis()));

            int noOrder = orderService.getOrdersByClazzId(order.getClazz().getId()).size();
            // lấy % revenue từ sys var
            // 0 <= revenue <= 1
            float revenue = Float.parseFloat(systemVariableService.getSysVarByVarKey("revenue").getValue());
            // lấy tuition từ request
            float amount = clazz.getRequest().getTuition();

            if (noOrder == 0) { // chưa có order tạo trong clazz, phụ huynh đóng tiền cho system
                order.setUser(userService.getUserById(clazz.getRequest().getParent().getId()).orElse(null));
                order.setType(1);
                order.setAmount(amount);
                userWalletService.withdraw(clazz.getRequest().getParent().getId(), amount);
                sysWalletService.deposit(amount);
                if (clazz.getStatus() == 0) {
                    clazz.setStatus(1);
                    clazzService.save(clazz);
                }

                // noti cho phụ huynh
                notificationService.add(order.getUser(),
                        "Đã đóng " + String.format("%,.2f", amount) + " cho lớp " + order.getClazz().getId()
                                + " thành công");
                // noti cho manager
                notificationService.add(order.getClazz().getRequest().getManager(),
                        "Đã nhận " + String.format("%,.2f", amount) + " phí tạo lớp "
                                + order.getClazz().getId() + " từ phụ huynh " + order.getUser().getFullname());
            } else if (noOrder == 1) { // đã có order phụ huynh đóng tiền, system trả tiền dạy cho gia sư
                float amountRevenue = amount * revenue;
                amount = amount * (1 - revenue);
                order.setUser(userService.getUserById(clazz.getTutor().getId()).orElse(null));
                order.setType(2);
                order.setAmount(amount);
                if (amount > sysWalletService.getBalance())
                    return ApiResponseDto.<CreateOrderResDto>builder()
                            .message("Số dư trong tài khoản hệ thống không đủ.")
                            .build();
                sysWalletService.withdraw(amount);
                userWalletService.deposit(clazz.getTutor().getId(), amount);
                // noti cho gia sư
                notificationService.add(order.getUser(),
                        "Đã nhận " + String.format("%,.2f", amount) + " học phí sau khi trả "
                                + String.format("%,.2f", amountRevenue)
                                + " phí cho hệ thống sau khi dạy lớp " + order.getClazz().getId()
                                + " thành công");
                // noti cho manager
                notificationService.add(order.getClazz().getRequest().getManager(),
                        "Đã chuyển " + String.format("%,.2f", amount) + " tiền cho gia sư "
                                + order.getUser().getFullname() + " từ dạy lớp " + order.getClazz().getId());
                notificationService.add(order.getClazz().getRequest().getManager(),
                        "Hệ thống đã thu được " + String.format("%,.2f", amountRevenue) + " tiền từ lớp "
                                + order.getClazz().getId());
            } else {
                return ApiResponseDto.<CreateOrderResDto>builder().responseCode("500")
                        .message("Lớp đã hoàn thành 2 order, không thể tạo thêm").build();
            }
            dto.fromOrder(orderService.save(order));
        } catch (Exception e) {
            return ApiResponseDto.<CreateOrderResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<CreateOrderResDto>builder().data(dto).build();
    }

    // class status = 8, dạy và trả chỉ 1 phần số buổi
    @PostMapping("/createPartly")
    public ApiResponseDto<CreateOrderResDto> createPartly(@RequestBody CreatePartlyOrderReqDto orderReqDto) {
        CreateOrderResDto dto = new CreateOrderResDto();
        try {

            Clazz clazz = clazzService.getClazzById(orderReqDto.getClazzId()).orElse(null);

            Order order = new Order();
            orderReqDto.toOrder(order);
            order.setClazz(clazz);
            order.setTimeCreate(new Date(System.currentTimeMillis()));

            int noOrder = orderService.getOrdersByClazzId(order.getClazz().getId()).size();
            // lấy % revenue từ sys var
            // 0 <= revenue <= 1
            float revenue = Float.parseFloat(systemVariableService.getSysVarByVarKey("revenue").getValue());
            // lấy tuition từ request
            float amount = orderReqDto.getAmount();

            if (noOrder == 1) { // đã có order phụ huynh đóng tiền, system trả 1 phần amount tiền dạy cho gia
                                // sư, còn lại cho phụ huynh
                float amountRefund = clazz.getRequest().getTuition() - amount;
                float amountRevenue = amount * revenue;
                amount = amount * (1 - revenue);
                order.setUser(userService.getUserById(clazz.getTutor().getId()).orElse(null));
                order.setType(2);
                order.setAmount(amount);
                if (amount > sysWalletService.getBalance())
                    return ApiResponseDto.<CreateOrderResDto>builder()
                            .message("Số dư trong tài khoản hệ thống không đủ.")
                            .build();
                sysWalletService.withdraw(amount);
                userWalletService.deposit(clazz.getTutor().getId(), amount);
                // noti cho gia sư
                notificationService.add(order.getUser(),
                        "Đã nhận " + String.format("%,.2f", amount) + " học phí sau khi trả "
                                + String.format("%,.2f", amountRevenue)
                                + " phí cho hệ thống sau khi dạy lớp " + order.getClazz().getId()
                                + " thành công");
                // noti cho manager
                notificationService.add(order.getClazz().getRequest().getManager(),
                        "Đã chuyển " + String.format("%,.2f", amount) + " tiền cho gia sư "
                                + order.getUser().getFullname() + " từ dạy lớp " + order.getClazz().getId());
                notificationService.add(order.getClazz().getRequest().getManager(),
                        "Hệ thống đã thu được " + String.format("%,.2f", amountRevenue) + " tiền từ lớp "
                                + order.getClazz().getId());

                // trả cho phụ ------------------------ amountRefund
                order.setUser(userService.getUserById(clazz.getRequest().getParent().getId()).orElse(null));
                order.setType(3); // refund cho phụ huynh
                order.setAmount(amountRefund);
                userWalletService.deposit(clazz.getRequest().getParent().getId(), amountRefund);
                sysWalletService.withdraw(amountRefund);
                // noti cho phụ huynh
                notificationService.add(order.getUser(),
                        "Đã nhận lại " + String.format("%,.2f", amountRefund) + " cho lớp " + order.getClazz().getId()
                                + " sau khi xem xét thành công");
                // noti cho manager
                notificationService.add(order.getClazz().getRequest().getManager(),
                        "Đã trả lại " + String.format("%,.2f", amountRefund) + " phí sau khi xem xét lớp "
                                + order.getClazz().getId() + " cho phụ huynh " + order.getUser().getFullname());
            } else {
                return ApiResponseDto.<CreateOrderResDto>builder().responseCode("500")
                        .message("Lớp đã hoàn thành 2 order, không thể tạo thêm").build();
            }
            dto.fromOrder(orderService.save(order));
        } catch (Exception e) {
            return ApiResponseDto.<CreateOrderResDto>builder().responseCode("500").message(e.getMessage()).build();
        }
        return ApiResponseDto.<CreateOrderResDto>builder().data(dto).build();
    }
}
