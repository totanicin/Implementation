package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.MediumCategory;

public interface MediumCategoryRepository extends JpaRepository<MediumCategory, Long> {
}