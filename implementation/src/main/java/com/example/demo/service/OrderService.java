package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Administrator;
import com.example.demo.entity.Order;
import com.example.demo.entity.Store;

public interface OrderService {
    void saveOrder(Order order);
    List<Order> getAllOrders();
    List<Order> getOrdersByStore(Store store);
    Order getOrderById(Long id);
    Administrator findAdminByUsername(String username);
}
