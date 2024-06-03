package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Administrator;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Store;
import com.example.demo.repository.AdministratorRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private ProductRepository productRepository;

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

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStore(Store store) {
        return orderRepository.findByStore(store);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findByIdWithAdmin(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Administrator findAdminByUsername(String username) {
        Optional<Administrator> adminOpt = administratorRepository.findByEmail(username);
        return adminOpt.orElseThrow(() -> new RuntimeException("管理者が見つかりませんでした"));
    }
}