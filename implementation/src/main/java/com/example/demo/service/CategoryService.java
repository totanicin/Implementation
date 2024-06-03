package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.LargeCategory;
import com.example.demo.model.MediumCategory;
import com.example.demo.model.SmallCategory;
import com.example.demo.repository.LargeCategoryRepository;
import com.example.demo.repository.MediumCategoryRepository;
import com.example.demo.repository.SmallCategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private LargeCategoryRepository largeCategoryRepository;

    @Autowired
    private MediumCategoryRepository mediumCategoryRepository;

    @Autowired
    private SmallCategoryRepository smallCategoryRepository;

    public List<LargeCategory> getAllLargeCategories() {
        return largeCategoryRepository.findAll();
    }

    public List<MediumCategory> getAllMediumCategories() {
        return mediumCategoryRepository.findAll();
    }

    public List<SmallCategory> getAllSmallCategories() {
        return smallCategoryRepository.findAll();
    }
}
