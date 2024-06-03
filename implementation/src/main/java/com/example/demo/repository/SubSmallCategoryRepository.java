package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SubSmallCategory;

public interface SubSmallCategoryRepository extends JpaRepository<SubSmallCategory, Long> {
    List<SubSmallCategory> findByMediumCategoryId(Long mediumCategoryId);
}
