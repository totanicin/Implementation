package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.MediumCategory;

public interface MediumCategoryRepository extends JpaRepository<MediumCategory, Long> {
}