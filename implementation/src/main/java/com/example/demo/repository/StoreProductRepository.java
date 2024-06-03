package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.StoreProduct;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
}
