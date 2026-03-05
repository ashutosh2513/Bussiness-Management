package com.smartbilling.rbac.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role_mst")
public class Role extends RbacAuditFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @Column(name = "role_code", nullable = false, unique = true)
    private String roleCode;

    @Column(name = "status", nullable = false)
    private Boolean status = true;
}
