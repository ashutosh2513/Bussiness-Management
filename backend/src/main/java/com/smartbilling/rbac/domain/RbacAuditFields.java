package com.smartbilling.rbac.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class RbacAuditFields {
    @Column(name = "crt_dt", updatable = false)
    private LocalDateTime crtDt;

    @Column(name = "lst_updt_dt")
    private LocalDateTime lstUpdtDt;

    @PrePersist
    void onCreate() {
        this.crtDt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.lstUpdtDt = LocalDateTime.now();
    }
}
