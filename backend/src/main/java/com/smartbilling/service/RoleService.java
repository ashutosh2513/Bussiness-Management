package com.smartbilling.service;

import com.smartbilling.rbac.domain.Role;

public interface RoleService {
    Role getRoleById(Long roleId);
    Role getRoleByCode(String roleCode);
}
