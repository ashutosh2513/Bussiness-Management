package com.smartbilling.service;

public interface ApiAuthorizationService {
    boolean checkApiPermission(Long roleId, String requestUrl, String httpMethod);
    void refreshRoleApiCache(Long roleId);
}
