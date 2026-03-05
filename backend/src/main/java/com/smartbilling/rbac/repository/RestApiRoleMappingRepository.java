package com.smartbilling.rbac.repository;

import com.smartbilling.rbac.domain.RestApiRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestApiRoleMappingRepository extends JpaRepository<RestApiRoleMapping, Long> {
    @Query("""
            select arm
            from RestApiRoleMapping arm
            join fetch arm.restApiMaster api
            where arm.role.roleId = :roleId
            """)
    List<RestApiRoleMapping> findByRoleRoleIdWithApi(@Param("roleId") Long roleId);
}
