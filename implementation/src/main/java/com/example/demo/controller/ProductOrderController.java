package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.StoreProduct;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ProductOrderController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job productAndOrderJob;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/batch/run")
    public ResponseEntity<String> runBatchJob() {
        try {
            JobExecution jobExecution = jobLauncher.run(productAndOrderJob, new JobParameters());
            return ResponseEntity.ok("Batch job has been invoked");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to run batch job");
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<StoreProduct>> getAllProducts() {
        List<StoreProduct> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getOrdersAndStores() {
        try {
            String jsonData = new String(Files.readAllBytes(Paths.get("batch-data.json")));
            Map<String, Object> response = new ObjectMapper().readValue(jsonData, HashMap.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
