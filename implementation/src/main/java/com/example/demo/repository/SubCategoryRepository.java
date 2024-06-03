package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
}
