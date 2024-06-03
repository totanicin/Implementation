package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.StoreProduct;
import com.example.demo.repository.LargeCategoryRepository;
import com.example.demo.repository.MediumCategoryRepository;
import com.example.demo.repository.SmallCategoryRepository;
import com.example.demo.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private LargeCategoryRepository largeCategoryRepository;

    @Autowired
    private MediumCategoryRepository mediumCategoryRepository;

    @Autowired
    private SmallCategoryRepository smallCategoryRepository;

    @GetMapping
    public String listProducts(Model model, 
                               @RequestParam(value = "search", required = false) String search, 
                               @RequestParam(value = "largeCategoryId", required = false) Long largeCategoryId, 
                               @RequestParam(value = "mediumCategoryId", required = false) Long mediumCategoryId, 
                               @RequestParam(value = "smallCategoryId", required = false) Long smallCategoryId,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<StoreProduct> productPage = productService.findByCriteria(search, largeCategoryId, mediumCategoryId, smallCategoryId, pageable);

        model.addAttribute("productPage", productPage);
        model.addAttribute("largeCategories", largeCategoryRepository.findAll());
        model.addAttribute("mediumCategories", mediumCategoryRepository.findAll());
        model.addAttribute("smallCategories", smallCategoryRepository.findAll());
        model.addAttribute("search", search);
        model.addAttribute("largeCategoryId", largeCategoryId);
        model.addAttribute("mediumCategoryId", mediumCategoryId);
        model.addAttribute("smallCategoryId", smallCategoryId);

        return "product_list";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Optional<StoreProduct> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
        } else {
            model.addAttribute("errorMessage", "商品が見つかりませんでした。");
        }
        return "product_detail";
    }
}
