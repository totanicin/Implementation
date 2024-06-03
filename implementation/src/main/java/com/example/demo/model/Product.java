package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Double costPrice;
    private Double manufacturerSuggestedRetailPrice;
    private String imageUrl; // 追加

    @ManyToOne
    private LargeCategory largeCategory;

    @ManyToOne
    private MediumCategory mediumCategory;

    @ManyToOne
    private SmallCategory smallCategory;

    // GetterとSetter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getManufacturerSuggestedRetailPrice() {
        return manufacturerSuggestedRetailPrice;
    }

    public void setManufacturerSuggestedRetailPrice(Double manufacturerSuggestedRetailPrice) {
        this.manufacturerSuggestedRetailPrice = manufacturerSuggestedRetailPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LargeCategory getLargeCategory() {
        return largeCategory;
    }

    public void setLargeCategory(LargeCategory largeCategory) {
        this.largeCategory = largeCategory;
    }

    public MediumCategory getMediumCategory() {
        return mediumCategory;
    }

    public void setMediumCategory(MediumCategory mediumCategory) {
        this.mediumCategory = mediumCategory;
    }

    public SmallCategory getSmallCategory() {
        return smallCategory;
    }

    public void setSmallCategory(SmallCategory smallCategory) {
        this.smallCategory = smallCategory;
    }
}