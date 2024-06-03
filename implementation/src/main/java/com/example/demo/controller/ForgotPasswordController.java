package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Administrator;
import com.example.demo.repository.AdministratorRepository;

@Controller
public class ForgotPasswordController {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin/forgot-password")
    public String showPasswordResetRequestForm() {
        return "forgot-password";
    }

    @PostMapping("/admin/forgot-password")
    public String handlePasswordResetRequest(@RequestParam String email, 
                                             @RequestParam String new_password, 
                                             @RequestParam String confirm_password, 
                                             Model model) {
        if (!new_password.equals(confirm_password)) {
            model.addAttribute("errorMessage", "新しいパスワードと確認パスワードが一致しません。");
            return "forgot-password";
        }

        Optional<Administrator> optionalAdmin = administratorRepository.findByEmail(email);
        if (!optionalAdmin.isPresent()) {
            model.addAttribute("errorMessage", "メールアドレスが見つかりません。");
            return "forgot-password";
        }

        Administrator administrator = optionalAdmin.get();
        administrator.setPassword(passwordEncoder.encode(new_password));
        administratorRepository.save(administrator);
        return "redirect:/admin/signin";
    }
}