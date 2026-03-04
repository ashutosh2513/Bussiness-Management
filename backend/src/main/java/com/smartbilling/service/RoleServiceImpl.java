package com.smartbilling.service;

import com.smartbilling.rbac.domain.Role;
import com.smartbilling.rbac.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .filter(Role::getStatus)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid roleId"));
    }

    @Override
    public Role getRoleByCode(String roleCode) {
        return roleRepository.findByRoleCodeIgnoreCaseAndStatusTrue(roleCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Role not configured"));
    }
}
