package com.smartbilling.service;

import com.smartbilling.dto.RbacDtos;

import java.util.List;

public interface PrivilegeService {
    List<RbacDtos.PrivilegeModuleDto> getPrivilegesForRole(Long roleId);
    List<RbacDtos.PrivilegeModuleDto> getPrivilegesForRoleCode(String roleCode);
    void refreshRolePrivilegeCache(Long roleId);
}
