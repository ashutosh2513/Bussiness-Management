package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="invoice_items")
public class InvoiceItem extends BaseEntity {
    @ManyToOne(optional = false)
    private Invoice invoice;
    @ManyToOne(optional = false)
    private Product product;
    private int quantity;
    private double unitPrice;
    private double taxRate;
    private double lineTotal;
}
