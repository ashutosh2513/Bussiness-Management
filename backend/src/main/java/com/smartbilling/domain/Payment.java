package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="payments")
public class Payment extends BaseEntity {
    @ManyToOne(optional = false)
    private Invoice invoice;
    @Enumerated(EnumType.STRING)
    private Enums.PaymentMethod method;
    private double amount;
    private String referenceNo;
}
