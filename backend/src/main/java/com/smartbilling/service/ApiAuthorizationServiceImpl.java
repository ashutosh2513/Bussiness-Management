package com.smartbilling.service;

import com.smartbilling.rbac.repository.RestApiRoleMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiAuthorizationServiceImpl implements ApiAuthorizationService {
    private final RestApiRoleMappingRepository restApiRoleMappingRepository;
    private final RoleService roleService;

    private final Map<Long, Set<String>> allowedApiCache = new ConcurrentHashMap<>();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean checkApiPermission(Long roleId, String requestUrl, String httpMethod) {
        roleService.getRoleById(roleId);
        String normalizedMethod = httpMethod.toUpperCase();
        Set<String> allowed = allowedApiCache.computeIfAbsent(roleId, this::loadAllowedApis);
        return allowed.stream().anyMatch(entry -> {
            String[] parts = entry.split("::", 2);
            if (parts.length != 2)
                return false;
            String method = parts[0];
            String pattern = parts[1];
            return method.equals(normalizedMethod) && pathMatcher.match(pattern, requestUrl);
        });
    }

    @Override
    public void refreshRoleApiCache(Long roleId) {
        allowedApiCache.remove(roleId);
    }

    private Set<String> loadAllowedApis(Long roleId) {
        return restApiRoleMappingRepository.findByRoleRoleIdWithApi(roleId).stream()
                .filter(m -> Boolean.TRUE.equals(m.getIsAllowed()))
                .filter(m -> Boolean.TRUE.equals(m.getRestApiMaster().getStatus()))
                .map(m -> m.getRestApiMaster().getHttpMethod().toUpperCase() + "::" + m.getRestApiMaster().getApiUrl())
                .collect(Collectors.toSet());
    }
}
