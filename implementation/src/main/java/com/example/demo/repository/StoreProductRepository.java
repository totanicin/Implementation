package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.StoreProduct;

public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {

    @Query("SELECT sp FROM StoreProduct sp WHERE " +
           "(:search IS NULL OR sp.product.name LIKE %:search%) AND " +
           "(:largeCategoryId IS NULL OR sp.product.largeCategory.id = :largeCategoryId) AND " +
           "(:mediumCategoryId IS NULL OR sp.product.mediumCategory.id = :mediumCategoryId) AND " +
           "(:smallCategoryId IS NULL OR sp.product.smallCategory.id = :smallCategoryId)")
    Page<StoreProduct> findByCriteria(@Param("search") String search, 
                                      @Param("largeCategoryId") Long largeCategoryId, 
                                      @Param("mediumCategoryId") Long mediumCategoryId, 
                                      @Param("smallCategoryId") Long smallCategoryId,
                                      Pageable pageable);
}
