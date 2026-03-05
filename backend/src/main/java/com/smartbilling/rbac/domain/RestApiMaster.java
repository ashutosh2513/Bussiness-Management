package com.smartbilling.rbac.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rest_api_mst")
public class RestApiMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_id")
    private Long apiId;

    @Column(name = "api_name", nullable = false)
    private String apiName;

    @Column(name = "api_url", nullable = false)
    private String apiUrl;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "module")
    private String module;

    @Column(name = "status", nullable = false)
    private Boolean status = true;
}
