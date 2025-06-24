package com.trackswiftly.utils.base.utils;

import lombok.extern.log4j.Log4j2;



/**
 * Utility class for managing tenant context in a multi-tenant application.
 * <p>
 * Stores the current tenant ID using {@link InheritableThreadLocal} so that
 * it can be accessed throughout the thread lifecycle, including child threads.
 */
@Log4j2
public final class TenantContext {
    

    /** Private constructor to prevent instantiation. */
    private TenantContext() {}


    private static InheritableThreadLocal<String> currentTenant = new InheritableThreadLocal<>();
    
    /**
     * Sets the current tenant ID in the thread-local context.
     *
     * @param tenantId the tenant ID to set
     */
    public static void setTenantId(String tenantId) {
        log.debug("Setting tenantId to " + tenantId);
        currentTenant.set(tenantId);
    }


    /**
     * Retrieves the current tenant ID from the thread-local context.
     *
     * @return the current tenant ID, or {@code null} if not set
     */
    public static String getTenantId() {
        return currentTenant.get();
    }


    /**
     * Clears the tenant ID from the thread-local context.
     */
    public static void clear(){
        currentTenant.remove();
    }
}
