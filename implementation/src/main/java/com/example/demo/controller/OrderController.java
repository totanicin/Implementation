package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Administrator;
import com.example.demo.entity.Order;
import com.example.demo.entity.Store;
import com.example.demo.entity.StoreProduct;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreService storeService;

    @GetMapping("/new")
    public String newOrderForm(@RequestParam("productId") Long productId, Model model, Principal principal) {
        Optional<StoreProduct> productOpt = productService.getProductById(productId);
        if (productOpt.isPresent()) {
            StoreProduct product = productOpt.get();
            Order order = new Order();
            order.setProduct(product.getProduct());
            model.addAttribute("order", order);
            model.addAttribute("product", product);

            String username = principal.getName();
            Administrator admin = orderService.findAdminByUsername(username);
            if (admin != null) {
                model.addAttribute("adminFullName", admin.getFirstName() + " " + admin.getLastName());
                model.addAttribute("storeName", admin.getStore().getName());
            } else {
                return "error/500";
            }

            return "order_form";
        } else {
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
                order.setProduct(product.getProduct());
                order.setAdmin(admin);
                order.setStore(admin.getStore());
                orderService.saveOrder(order);
                productService.updateStock(productId, order.getQuantity());
                return "redirect:/orders/history";
            } else {
                return "error/404";
            }
        } else {
            return "error/500";
        }
    }

    @GetMapping("/history")
    public String listOrders(Model model, Principal principal) {
        String username = principal.getName();
        Administrator admin = orderService.findAdminByUsername(username);
        if (admin != null) {
            List<Order> orders = orderService.getOrdersByStore(admin.getStore());
            List<Store> stores = List.of(admin.getStore()); // ログインユーザーの所属店舗だけ取得
            model.addAttribute("orders", orders);
            model.addAttribute("stores", stores); // ログインユーザーの店舗をモデルに追加
            model.addAttribute("adminFullName", admin.getFirstName() + " " + admin.getLastName());
            model.addAttribute("storeName", admin.getStore().getName());
            return "order_history";
        } else {
            return "error/500";
        }
    }
}
