package com.smartbilling.rbac.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role_dtls")
public class RoleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_dtl_id")
    private Long roleDtlId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = java.time.LocalDateTime.now();
    }
}
