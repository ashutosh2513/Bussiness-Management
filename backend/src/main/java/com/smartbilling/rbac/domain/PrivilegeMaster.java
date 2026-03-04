package com.smartbilling.rbac.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "priv_mst")
public class PrivilegeMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priv_id")
    private Long privId;

    @Column(name = "priv_name", nullable = false, unique = true)
    private String privName;

    @Column(name = "priv_code", nullable = false, unique = true)
    private String privCode;

    @Column(name = "icon")
    private String icon;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "status", nullable = false)
    private Boolean status = true;
}
