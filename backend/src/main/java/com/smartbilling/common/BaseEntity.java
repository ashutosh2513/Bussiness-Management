package com.smartbilling.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "crt_dt", nullable = false, updatable = false)
    private Instant crtDt;

    @Column(name = "lst_updt_dt")
    private Instant lstUpdtDt;

    @PrePersist
    void onCreate() {
        this.crtDt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        this.lstUpdtDt = Instant.now();
    }
}
