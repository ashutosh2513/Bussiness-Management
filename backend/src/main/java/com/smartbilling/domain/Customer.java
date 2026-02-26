package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name = "customers")
public class Customer extends BaseEntity {
    @Column(nullable = false)
    private String name;
    private String phone;
    private String gstNumber;
    private String address;
    private double outstandingBalance;
}
