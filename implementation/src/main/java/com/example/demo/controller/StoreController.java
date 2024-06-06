package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Administrator;
import com.example.demo.entity.Store;
import com.example.demo.service.AdminService;
import com.example.demo.service.StoreService;

@Controller
@RequestMapping("/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String listStores(Model model) {
        // ログインユーザーの情報を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();  // ログインユーザーのメールアドレスを取得
        Administrator administrator = adminService.findByEmail(email);

        // 全ての店舗情報を取得
        List<Store> stores = storeService.getAllStores();

        // ログインユーザーの所属店舗情報を取得
        Store userStore = administrator.getStore();

        model.addAttribute("stores", stores);
        model.addAttribute("userStore", userStore);

        return "store_list";
    }

    @GetMapping("/{id}")
    public String viewStore(@PathVariable Long id, Model model) {
        Store store = storeService.getStoreById(id).orElseThrow(() -> new IllegalArgumentException("Invalid store Id:" + id));
        model.addAttribute("store", store);
        return "store_detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("store", new Store());
        return "store_create";
    }

    @PostMapping
    public String createStore(@ModelAttribute Store store) {
        storeService.createStore(store);
        return "redirect:/stores";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Store store = storeService.getStoreById(id).orElseThrow(() -> new IllegalArgumentException("Invalid store Id:" + id));
        model.addAttribute("store", store);
        return "store_edit";
    }

    @PostMapping("/{id}/edit")
    public String updateStore(@PathVariable Long id, @ModelAttribute Store store) {
        store.setId(id);
        storeService.updateStore(store);
        return "redirect:/stores/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return "redirect:/stores";
    }
}
