package com.smartbilling.dto;

import java.util.List;

public class RbacDtos {
    public record PrivilegeChildDto(
            Long privilegeId,
            String name,
            String code,
            boolean allowed
    ) {}

    public record PrivilegeModuleDto(
            Long moduleId,
            String module,
            String moduleCode,
            String icon,
            Integer displayOrder,
            List<PrivilegeChildDto> children
    ) {}
}
