package com.smartbilling.rbac.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "priv_role_mpg")
public class PrivilegeRoleMapping extends RbacAuditFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "priv_dtl_id", nullable = false)
    private PrivilegeDetails privilegeDetails;

    @Column(name = "is_allowed", nullable = false)
    private Boolean isAllowed = true;
}
