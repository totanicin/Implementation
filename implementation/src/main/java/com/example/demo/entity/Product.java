package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
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
}
