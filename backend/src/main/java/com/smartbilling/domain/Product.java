package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="products")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false)
    private Category category;
    @ManyToOne(optional = false)
    private Unit unit;
    @Column(nullable = false, unique = true)
    private String sku;
    @Column(nullable = false, unique = true)
    private String barcode;
    private double price;
    private double taxRate;
    private int lowStockThreshold = 5;
}
