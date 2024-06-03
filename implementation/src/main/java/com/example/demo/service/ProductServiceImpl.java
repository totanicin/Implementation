package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.StoreProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreProductRepository storeProductRepository;

    @Override
    public Page<StoreProduct> getAllProducts(Pageable pageable) {
        return storeProductRepository.findAll(pageable);
    }

    @Override
    public Page<StoreProduct> findByCriteria(String search, Long largeCategoryId, Long mediumCategoryId, Long smallCategoryId, Pageable pageable) {
        // TODO: カスタムクエリを実装する
        return storeProductRepository.findAll(pageable); // 簡略化のための例
    }

    @Override
    public Optional<StoreProduct> getProductById(Long id) {
        return storeProductRepository.findById(id);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateStock(Long storeProductId, int quantity) {
        Optional<StoreProduct> productOpt = storeProductRepository.findById(storeProductId);
        if (productOpt.isPresent()) {
            StoreProduct product = productOpt.get();
            product.setStock(product.getStock() + quantity); // 在庫数を発注数だけ増加させる
            storeProductRepository.save(product);
        }
    }
}
