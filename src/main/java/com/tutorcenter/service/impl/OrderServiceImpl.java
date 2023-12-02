package com.tutorcenter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tutorcenter.model.Order;
import com.tutorcenter.repository.OrderRepository;
import com.tutorcenter.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getOrdersById(List<Integer> idList) {
        return orderRepository.findAllById(idList);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByClazzId(int cId) {
        List<Order> response = new ArrayList<>();
        for (Order o : orderRepository.findAll()) {
            if (o.getClazz().getId() == cId)
                response.add(o);
        }
        return response;
    }

}
