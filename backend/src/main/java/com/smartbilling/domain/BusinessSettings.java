package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="business_settings")
public class BusinessSettings extends BaseEntity {
    @Column(nullable = false)
    private String businessName;
    private String logoUrl;
    private String address;
    private String gstin;
    private String signatureUrl;
    private String invoicePrefix = "INV";
    private double defaultTaxRate = 18.0;
    private String theme = "light";
}
