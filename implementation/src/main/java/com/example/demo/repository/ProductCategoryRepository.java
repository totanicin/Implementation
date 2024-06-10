package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findBySmallCategoryId(Long smallCategoryId);
}
