package com.smartbilling.rbac.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "priv_prnt_mpg")
public class PrivilegeParentMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "priv_id", nullable = false)
    private PrivilegeMaster privilegeMaster;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "priv_dtl_id", nullable = false)
    private PrivilegeDetails privilegeDetails;

    @Column(name = "display_order")
    private Integer displayOrder;
}
