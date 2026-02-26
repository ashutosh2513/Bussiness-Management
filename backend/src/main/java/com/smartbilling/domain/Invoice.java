package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity @Table(name="invoices")
public class Invoice extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String invoiceNo;
    private LocalDate invoiceDate = LocalDate.now();
    @ManyToOne(optional = false)
    private Customer customer;
    private double subtotal;
    private double taxAmount;
    private double grandTotal;
    private double paidAmount;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items = new ArrayList<>();
}
