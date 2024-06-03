package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SmallCategory;

public interface SmallCategoryRepository extends JpaRepository<SmallCategory, Long> {
}