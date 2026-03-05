package com.smartbilling.service;

import com.smartbilling.dto.RbacDtos;
import com.smartbilling.rbac.domain.PrivilegeParentMapping;
import com.smartbilling.rbac.domain.PrivilegeRoleMapping;
import com.smartbilling.rbac.domain.Role;
import com.smartbilling.rbac.repository.PrivilegeParentMappingRepository;
import com.smartbilling.rbac.repository.PrivilegeRoleMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
    private final RoleService roleService;
    private final PrivilegeParentMappingRepository privilegeParentMappingRepository;
    private final PrivilegeRoleMappingRepository privilegeRoleMappingRepository;

    private final Map<Long, List<RbacDtos.PrivilegeModuleDto>> privilegeCache = new ConcurrentHashMap<>();

    @Override
    public List<RbacDtos.PrivilegeModuleDto> getPrivilegesForRole(Long roleId) {
        return privilegeCache.computeIfAbsent(roleId, this::loadPrivileges);
    }

    @Override
    public List<RbacDtos.PrivilegeModuleDto> getPrivilegesForRoleCode(String roleCode) {
        Role role = roleService.getRoleByCode(roleCode);
        return getPrivilegesForRole(role.getRoleId());
    }

    @Override
    public void refreshRolePrivilegeCache(Long roleId) {
        privilegeCache.remove(roleId);
    }

    @Transactional(readOnly = true)
    private List<RbacDtos.PrivilegeModuleDto> loadPrivileges(Long roleId) {
        roleService.getRoleById(roleId);

        Map<Long, Boolean> allowedByPrivDtlId = privilegeRoleMappingRepository.findByRoleRoleIdWithDetails(roleId).stream()
                .collect(Collectors.toMap(
                        mapping -> mapping.getPrivilegeDetails().getPrivDtlId(),
                        PrivilegeRoleMapping::getIsAllowed,
                        (left, right) -> right
                ));

        Map<Long, RbacDtos.PrivilegeModuleDto> modules = new LinkedHashMap<>();
        for (PrivilegeParentMapping mapping : privilegeParentMappingRepository.findAllWithMasterAndDetailsOrdered()) {
            if (!Boolean.TRUE.equals(mapping.getPrivilegeMaster().getStatus()) ||
                    !Boolean.TRUE.equals(mapping.getPrivilegeDetails().getStatus())) {
                continue;
            }

            RbacDtos.PrivilegeModuleDto existing = modules.get(mapping.getPrivilegeMaster().getPrivId());
            List<RbacDtos.PrivilegeChildDto> children = existing == null
                    ? new ArrayList<>()
                    : new ArrayList<>(existing.children());

            children.add(new RbacDtos.PrivilegeChildDto(
                    mapping.getPrivilegeDetails().getPrivDtlId(),
                    mapping.getPrivilegeDetails().getPrivName(),
                    mapping.getPrivilegeDetails().getPrivCode(),
                    Boolean.TRUE.equals(allowedByPrivDtlId.get(mapping.getPrivilegeDetails().getPrivDtlId()))
            ));

            modules.put(
                    mapping.getPrivilegeMaster().getPrivId(),
                    new RbacDtos.PrivilegeModuleDto(
                            mapping.getPrivilegeMaster().getPrivId(),
                            mapping.getPrivilegeMaster().getPrivName(),
                            mapping.getPrivilegeMaster().getPrivCode(),
                            mapping.getPrivilegeMaster().getIcon(),
                            mapping.getPrivilegeMaster().getDisplayOrder(),
                            children
                    )
            );
        }

        return modules.values().stream()
                .sorted(Comparator.comparing(module -> Optional.ofNullable(module.displayOrder()).orElse(Integer.MAX_VALUE)))
                .toList();
    }
}
