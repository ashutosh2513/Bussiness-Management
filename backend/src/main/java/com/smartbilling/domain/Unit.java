package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="units")
public class Unit extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;
    private String label;
}
