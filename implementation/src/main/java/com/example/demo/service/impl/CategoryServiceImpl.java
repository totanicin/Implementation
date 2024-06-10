package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LargeCategory;
import com.example.demo.entity.MediumCategory;
import com.example.demo.entity.SmallCategory;
import com.example.demo.repository.LargeCategoryRepository;
import com.example.demo.repository.MediumCategoryRepository;
import com.example.demo.repository.SmallCategoryRepository;
import com.example.demo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private LargeCategoryRepository largeCategoryRepository;

    @Autowired
    private MediumCategoryRepository mediumCategoryRepository;

    @Autowired
    private SmallCategoryRepository smallCategoryRepository;

    @Override
    public List<LargeCategory> getAllLargeCategories() {
        return largeCategoryRepository.findAll();
    }

    @Override
    public List<MediumCategory> getAllMediumCategories() {
        return mediumCategoryRepository.findAll();
    }

    @Override
    public List<SmallCategory> getAllSmallCategories() {
        return smallCategoryRepository.findAll();
    }
}
