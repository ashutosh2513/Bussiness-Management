package com.smartbilling.domain;

import com.smartbilling.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity @Table(name="categories")
public class Category extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    private Category parent;
}
