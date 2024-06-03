package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.model.Product;
import com.example.demo.model.StoreProduct;

public interface ProductService {
    Page<StoreProduct> getAllProducts(Pageable pageable);
    Page<StoreProduct> findByCriteria(String search, Long largeCategoryId, Long mediumCategoryId, Long smallCategoryId, Pageable pageable);
    Optional<StoreProduct> getProductById(Long id);
    void saveProduct(Product product);
    void updateStock(Long storeProductId, int quantity); // 在庫数を更新するメソッドを追加
}
