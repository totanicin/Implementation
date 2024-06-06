package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Administrator;
import com.example.demo.entity.Manufacturer;
import com.example.demo.repository.ManufacturerRepository;
import com.example.demo.service.AdminService;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String listManufacturers(Model model) {
        model.addAttribute("manufacturers", manufacturerRepository.findAll());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Administrator admin = adminService.findByEmail(userDetails.getUsername());
            model.addAttribute("isAdmin", admin != null && admin.getPermissions().stream()
                .anyMatch(permission -> "管理者".equals(permission.getName())));
        }

        return "manufacturer_list";
    }

    @GetMapping("/new")
    public String newManufacturerForm(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "manufacturer_new";
    }

    @PostMapping
    public String createManufacturer(@Valid @ModelAttribute Manufacturer manufacturer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "入力内容に誤りがあります。");
            return "manufacturer_new";
        }
        manufacturerRepository.save(manufacturer);
        return "redirect:/manufacturers";
    }

    @GetMapping("/{id}")
    public String viewManufacturer(@PathVariable Long id, Model model) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid manufacturer Id:" + id));
        model.addAttribute("manufacturer", manufacturer);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Administrator admin = adminService.findByEmail(userDetails.getUsername());
            model.addAttribute("isAdmin", admin != null && admin.getPermissions().stream()
                .anyMatch(permission -> "管理者".equals(permission.getName())));
        }

        return "manufacturer_details";
    }

    @GetMapping("/{id}/edit")
    public String editManufacturerForm(@PathVariable Long id, Model model) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid manufacturer Id:" + id));
        model.addAttribute("manufacturer", manufacturer);
        return "manufacturer_edit";
    }

    @PostMapping("/{id}/edit")
    public String updateManufacturer(@PathVariable Long id, @Valid @ModelAttribute Manufacturer manufacturer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("message", "入力内容に誤りがあります。");
            return "manufacturer_edit";
        }
        manufacturer.setId(id);
        manufacturerRepository.save(manufacturer);
        redirectAttributes.addFlashAttribute("message", "メーカ情報が更新されました。");
        return "redirect:/manufacturers/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteManufacturer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid manufacturer Id:" + id));
        manufacturerRepository.delete(manufacturer);
        redirectAttributes.addFlashAttribute("message", "メーカ情報が削除されました。");
        return "redirect:/manufacturers";
    }
}
