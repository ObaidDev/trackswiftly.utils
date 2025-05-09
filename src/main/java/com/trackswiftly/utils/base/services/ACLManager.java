package com.trackswiftly.utils.base.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class ACLManager {

    final static String METHODS = "methods";
    final static String IDS = "ids";


    public Map<String, Map<String, Set<String>>> convertToSet(Map<String, Map<String, List<String>>> rawAcl) {

        log.info("Converting ACL to Set...");

        Map<String, Map<String, Set<String>>> optimizedAcl = new HashMap<>();
        for (var entry : rawAcl.entrySet()) {
            String uri = entry.getKey();
            Map<String, List<String>> permissions = entry.getValue();

            Map<String, Set<String>> optimizedPermissions = new HashMap<>();
            optimizedPermissions.put(METHODS, new HashSet<>(permissions.getOrDefault(METHODS, List.of())));
            optimizedPermissions.put(IDS, new HashSet<>(permissions.getOrDefault(IDS, List.of())));

            optimizedAcl.put(uri, optimizedPermissions);
        }

        log.info("Converted ACL: " , optimizedAcl);

        return optimizedAcl;
    }



    public boolean hasAccess(Map<String, Map<String, Set<String>>> aclTable, String uri, String method, List<String> itemIds) {
        
        if (!aclTable.containsKey(uri)) {
            log.warn("URI not found in ACL table: " , uri);
            return false; // URI not found
        }

        Map<String, Set<String>> permissions = aclTable.get(uri);
        Set<String> allowedMethods = permissions.getOrDefault(METHODS, Set.of());

        if (!allowedMethods.contains(method)) {
            log.warn("Method not allowed: " , method);
            return false; // Method not allowed
        }

        Set<String> allowedIds = permissions.getOrDefault(IDS, Set.of());

        // If no specific IDs are enforced, allow access
        if (allowedIds.isEmpty()) {
            log.info("No specific IDs enforced, access granted.");
            return true;
        }

        log.info("Checking if item IDs are allowed: " , itemIds);
        // ✅ More Efficient Check Using `Set.containsAll()`
        return allowedIds.containsAll(itemIds);
    }


    /**
     * utility method to check if the token is valid
     * @param token
     * @param uri
     * @param method
     */
    public Map<String, Map<String, Set<String>>> getAclTable (Map<String, Object> tokenInfo) {

        @SuppressWarnings("unchecked")
        Map<String, Map<String, List<String>>> rawAcl = (Map<String, Map<String, List<String>>>) (Map<?, ?>) tokenInfo;

        return convertToSet(rawAcl);
    }



    public void testEncodeDecode(Map<String, Map<String, Set<String>>> aclTable){

        String comporedAcl = CompressedAclService.compressAcl(aclTable);

        log.info("Compressed ACL: " + comporedAcl);

        Map<String, Map<String, Set<String>>> decompressedAcl = CompressedAclService.decompressAcl(comporedAcl);

        log.info("Decompressed ACL: " + decompressedAcl);
    }
    
}
