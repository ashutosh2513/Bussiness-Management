package com.smartbilling.rbac.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "priv_dtls")
public class PrivilegeDetails extends RbacAuditFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priv_dtl_id")
    private Long privDtlId;

    @Column(name = "priv_name", nullable = false)
    private String privName;

    @Column(name = "priv_code", nullable = false, unique = true)
    private String privCode;

    @Column(name = "status", nullable = false)
    private Boolean status = true;
}
