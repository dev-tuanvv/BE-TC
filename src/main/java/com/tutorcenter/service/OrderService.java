package com.tutorcenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorcenter.model.Order;

@Service
public interface OrderService {
    List<Order> findAll();

    Optional<Order> getOrderById(int id);

    List<Order> getOrdersById(List<Integer> idList);

    Order save(Order order);

    List<Order> getOrdersByClazzId(int cId);
}
