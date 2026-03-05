package com.smartbilling.web;

import com.smartbilling.dto.RbacDtos;
import com.smartbilling.service.ApiAuthorizationService;
import com.smartbilling.service.PrivilegeService;
import com.smartbilling.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rbac")
@RequiredArgsConstructor
public class RbacController {
    private final PrivilegeService privilegeService;
    private final RoleService roleService;
    private final ApiAuthorizationService apiAuthorizationService;

    @GetMapping("/roles/{roleId}/privileges")
    public List<RbacDtos.PrivilegeModuleDto> getPrivilegesForRole(@PathVariable Long roleId) {
        return privilegeService.getPrivilegesForRole(roleId);
    }

    @GetMapping("/privileges/me")
    public List<RbacDtos.PrivilegeModuleDto> getPrivilegesForCurrentUser(Authentication authentication) {
        String roleCode = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("ROLE_"))
                .findFirst()
                .map(a -> a.substring("ROLE_".length()))
                .orElseThrow(() -> new IllegalArgumentException("Role not found in authentication"));

        Long roleId = roleService.getRoleByCode(roleCode).getRoleId();
        return privilegeService.getPrivilegesForRole(roleId);
    }

    @PostMapping("/roles/{roleId}/cache/refresh")
    public void refreshRoleCache(@PathVariable Long roleId) {
        privilegeService.refreshRolePrivilegeCache(roleId);
        apiAuthorizationService.refreshRoleApiCache(roleId);
    }
}
