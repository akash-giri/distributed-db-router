package org.example.service;

import org.example.annotation.ReadOnlyConnection;
import org.example.entity.Order;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Order createOrder(Order order) {
        return repository.save(order);
    }

    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return repository.findAll();
    }
}