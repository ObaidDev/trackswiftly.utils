package com.trackswiftly.utils.base.utils;

import java.util.UUID;

import lombok.extern.log4j.Log4j2;


@Log4j2
public final class TenantContext {
    
    private TenantContext() {}

    private static InheritableThreadLocal<UUID> currentTenant = new InheritableThreadLocal<>();

    public static void setTenantId(UUID tenantId) {
        log.debug("Setting tenantId to " + tenantId);
        currentTenant.set(tenantId);
    }

    public static UUID getTenantId() {
        return currentTenant.get();
    }

    public static void clear(){
        currentTenant.remove();
    }
}
