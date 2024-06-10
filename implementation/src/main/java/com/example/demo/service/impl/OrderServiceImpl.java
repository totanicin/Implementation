package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Administrator;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.repository.AdministratorRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void saveOrder(Order order) {
        if (order.getProduct() == null || order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Product and quantity must be set before saving an order.");
        }

        Product product = order.getProduct();
        int newStock = product.getStock() + order.getQuantity(); // 在庫数を加算

        if (newStock < 0) {
            throw new IllegalArgumentException("Insufficient stock for the product.");
        }

        product.setStock(newStock); // 更新後の在庫数を設定
        productRepository.save(product); // 在庫数をデータベースに保存

        order.setTotalPrice(product.getCostPrice() * order.getQuantity()); // 合計金額を設定
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order); // 注文をデータベースに保存
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByStore(Store store) {
        return orderRepository.findByStore(store);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findByIdWithAdmin(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Administrator findAdminByUsername(String username) {
        Optional<Administrator> adminOpt = administratorRepository.findByEmail(username);
        return adminOpt.orElseThrow(() -> new RuntimeException("管理者が見つかりませんでした"));
    }
}
