package com.smartbilling.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // Kept for backward compatibility with existing billing logic.
    private double price;

    // Kept for backward compatibility with existing billing logic.
    private double taxRate;

    private double purchasePrice;
    private double sellingPrice;
    private double gstPercent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "retailer_id", nullable = false)
    private User retailer;

    private int lowStockThreshold = 5;
}
