package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="inventory_movements")
public class InventoryMovement extends BaseEntity {
    @ManyToOne(optional = false)
    private Product product;
    private int quantity;
    private String movementType; // STOCK_IN/STOCK_OUT/AUDIT
    private String batchNo;
    private String note;
}
