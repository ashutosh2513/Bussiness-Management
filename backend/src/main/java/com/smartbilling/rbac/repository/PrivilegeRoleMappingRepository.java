package com.smartbilling.rbac.repository;

import com.smartbilling.rbac.domain.PrivilegeRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrivilegeRoleMappingRepository extends JpaRepository<PrivilegeRoleMapping, Long> {
    @Query("""
            select prm
            from PrivilegeRoleMapping prm
            join fetch prm.privilegeDetails pd
            where prm.role.roleId = :roleId
            """)
    List<PrivilegeRoleMapping> findByRoleRoleIdWithDetails(@Param("roleId") Long roleId);
}
