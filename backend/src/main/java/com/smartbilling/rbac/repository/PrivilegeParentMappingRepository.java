package com.smartbilling.rbac.repository;

import com.smartbilling.rbac.domain.PrivilegeParentMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PrivilegeParentMappingRepository extends JpaRepository<PrivilegeParentMapping, Long> {
    @Query("""
            select ppm
            from PrivilegeParentMapping ppm
            join fetch ppm.privilegeMaster pm
            join fetch ppm.privilegeDetails pd
            order by pm.privId asc, ppm.displayOrder asc, ppm.id asc
            """)
    List<PrivilegeParentMapping> findAllWithMasterAndDetailsOrdered();
}
