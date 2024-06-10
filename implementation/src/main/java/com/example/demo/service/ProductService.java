package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Product;
import com.example.demo.entity.StoreProduct;

public interface ProductService {
    Page<StoreProduct> getAllProducts(Pageable pageable);
    Page<StoreProduct> findByCriteria(String search, Long largeCategoryId, Long mediumCategoryId, Long smallCategoryId, Pageable pageable);
    Optional<StoreProduct> getProductById(Long id);
    void saveProduct(Product product);
    void updateStock(Long storeProductId, int quantity);
    List<StoreProduct> getAllProducts(); // 引数なしのメソッドを追加
}
