package com.example.demo.batch;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.entity.Order;
import com.example.demo.entity.StoreProduct;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Bean
    public Job productAndOrderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return jobBuilderFactory.get("productAndOrderJob")
                .start(productAndOrderStep(transactionManager))
                .build();
    }

    @Bean
    public Step productAndOrderStep(PlatformTransactionManager transactionManager) {
        return stepBuilderFactory.get("productAndOrderStep")
                .tasklet(productAndOrderTasklet())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public Tasklet productAndOrderTasklet() {
        return (contribution, chunkContext) -> {
            List<StoreProduct> products = productService.getAllProducts();
            List<Order> orders = orderService.getAllOrders();

            Map<String, Object> data = new HashMap<>();
            data.put("products", products);
            data.put("orders", orders);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonData = objectMapper.writeValueAsString(data);

            Files.write(Paths.get("batch-data.json"), jsonData.getBytes());

            return RepeatStatus.FINISHED;
        };
    }
}
