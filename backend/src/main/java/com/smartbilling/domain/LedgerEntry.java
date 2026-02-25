package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="ledger_entries")
public class LedgerEntry extends BaseEntity {
    @ManyToOne(optional = false)
    private Customer customer;
    @ManyToOne
    private Invoice invoice;
    @Enumerated(EnumType.STRING)
    private Enums.LedgerType type;
    private double amount;
    private String narration;
}
