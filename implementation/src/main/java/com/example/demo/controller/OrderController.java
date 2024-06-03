package com.example.demo.controller;

import java.security.Principal;
import java.util.List; // ここを追加
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Administrator;
import com.example.demo.model.Order;
import com.example.demo.model.StoreProduct;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/new")
    public String newOrderForm(@RequestParam("productId") Long productId, Model model, Principal principal) {
        Optional<StoreProduct> productOpt = productService.getProductById(productId);
        if (productOpt.isPresent()) {
            StoreProduct product = productOpt.get();
            Order order = new Order();
            order.setProduct(product.getProduct()); // StoreProductの内部のProductを取得
            model.addAttribute("order", order);
            model.addAttribute("product", product);

            String username = principal.getName();
            Administrator admin = orderService.findAdminByUsername(username);
            if (admin != null) {
                model.addAttribute("adminFullName", admin.getFirstName() + " " + admin.getLastName());
                model.addAttribute("storeName", admin.getStore().getName());
            } else {
                // 管理者が見つからない場合の処理
                return "error/500";
            }

            return "order_form";
        } else {
            // 商品が見つからない場合の処理
            return "error/404";
        }
    }

    @PostMapping
    public String createOrder(@ModelAttribute Order order, Principal principal, @RequestParam("productId") Long productId) {
        String username = principal.getName();
        Administrator admin = orderService.findAdminByUsername(username);
        if (admin != null) {
            Optional<StoreProduct> productOpt = productService.getProductById(productId);
            if (productOpt.isPresent()) {
                StoreProduct product = productOpt.get();
                order.setProduct(product.getProduct()); // StoreProductの内部のProductを取得
                order.setAdmin(admin);
                order.setStore(admin.getStore());
                orderService.saveOrder(order);

                // 在庫数を更新
                productService.updateStock(productId, order.getQuantity());

                return "redirect:/orders/history";
            } else {
                // 商品が見つからない場合の処理
                return "error/404";
            }
        } else {
            // 管理者が見つからない場合の処理
            return "error/500";
        }
    }

    @GetMapping("/history")
    public String listOrders(Model model, Principal principal) {
        String username = principal.getName();
        Administrator admin = orderService.findAdminByUsername(username);
        if (admin != null) {
            List<Order> orders = orderService.getOrdersByStore(admin.getStore());
            model.addAttribute("orders", orders);
            model.addAttribute("adminFullName", admin.getFirstName() + " " + admin.getLastName());
            model.addAttribute("storeName", admin.getStore().getName());
            return "order_history";
        } else {
            // 管理者が見つからない場合の処理
            return "error/500";
        }
    }
}
