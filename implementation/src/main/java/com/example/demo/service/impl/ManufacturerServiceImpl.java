package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Manufacturer;
import com.example.demo.repository.ManufacturerRepository;
import com.example.demo.service.ManufacturerService;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }
}
