package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.MainCategory;
import com.example.demo.entity.MediumCategory;
import com.example.demo.entity.SubSmallCategory;
import com.example.demo.repository.MainCategoryRepository;
import com.example.demo.repository.MediumCategoryRepository;
import com.example.demo.repository.ProductCategoryRepository;
import com.example.demo.repository.SubSmallCategoryRepository;

@Controller
@RequestMapping("/categories")
public class MainCategoryController {

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    @Autowired
    private MediumCategoryRepository mediumCategoryRepository;

    @Autowired
    private SubSmallCategoryRepository subSmallCategoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @GetMapping("/main")
    public String listMainCategories(Model model) {
        model.addAttribute("mainCategories", mainCategoryRepository.findAll());
        return "main_category_list";
    }

    @GetMapping("/main/{id}")
    public String viewMainCategory(@PathVariable Long id, Model model) {
        MainCategory mainCategory = mainCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid main category Id:" + id));
        model.addAttribute("mainCategory", mainCategory);
        model.addAttribute("subCategories", mainCategory.getSubCategories());
        return "main_category_details";
    }

    @GetMapping("/medium/{id}")
    public String viewMediumCategory(@PathVariable Long id, Model model) {
        MediumCategory mediumCategory = mediumCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid medium category Id:" + id));
        model.addAttribute("mediumCategory", mediumCategory);
        model.addAttribute("smallCategories", subSmallCategoryRepository.findByMediumCategoryId(id));
        return "medium_category_details";
    }

    @GetMapping("/small/{id}")
    public String viewSmallCategory(@PathVariable Long id, Model model) {
        SubSmallCategory subSmallCategory = subSmallCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid small category Id:" + id));
        model.addAttribute("smallCategory", subSmallCategory);
        model.addAttribute("products", productCategoryRepository.findBySmallCategoryId(id));
        return "small_category_details";
    }
}
