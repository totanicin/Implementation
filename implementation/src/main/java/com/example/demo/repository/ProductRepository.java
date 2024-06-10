package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
           "(:search IS NULL OR p.name LIKE %:search%) AND " +
           "(:largeCategoryId IS NULL OR p.largeCategory.id = :largeCategoryId) AND " +
           "(:mediumCategoryId IS NULL OR p.mediumCategory.id = :mediumCategoryId) AND " +
           "(:smallCategoryId IS NULL OR p.smallCategory.id = :smallCategoryId)")
    Page<Product> findProductsByCriteria(@Param("search") String search, 
                                         @Param("largeCategoryId") Long largeCategoryId, 
                                         @Param("mediumCategoryId") Long mediumCategoryId, 
                                         @Param("smallCategoryId") Long smallCategoryId,
                                         Pageable pageable);
}