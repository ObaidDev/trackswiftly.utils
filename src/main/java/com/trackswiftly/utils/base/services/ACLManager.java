package com.trackswiftly.utils.base.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;



/**
 * Utility class for managing Access Control Lists (ACL).
 * Provides methods to convert ACL data structures, check access permissions,
 * and test ACL compression/decompression.
 */
@Log4j2
public class ACLManager {

    /** Key representing allowed HTTP methods in the ACL structure. */
    final static String METHODS = "methods";


    /** Key representing allowed item IDs in the ACL structure. */
    final static String IDS = "ids";

    /**
     * Converts a raw ACL map (with list-based permissions) into a more optimized
     * structure using sets for faster lookup.
     *
     * @param rawAcl A map where each URI is associated with another map containing method and ID lists.
     * @return A map with the same structure but with sets instead of lists for faster access checks.
    */
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


    /**
     * Checks whether a specific request (URI + method + item IDs) is allowed based on the given ACL table.
     *
     * @param aclTable The ACL table with URI-based access rules.
     * @param uri      The request URI.
     * @param method   The HTTP method (e.g., GET, POST).
     * @param itemIds  The list of item IDs being accessed.
     * @return {@code true} if access is allowed, {@code false} otherwise.
     */
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
     * Retrieves and converts ACL data from a token payload.
     *
     * @param tokenInfo The token payload containing ACL rules.
     * @return A map with optimized ACL data using sets.
     */
    public Map<String, Map<String, Set<String>>> getAclTable (Map<String, Object> tokenInfo) {

        @SuppressWarnings("unchecked")
        Map<String, Map<String, List<String>>> rawAcl = (Map<String, Map<String, List<String>>>) (Map<?, ?>) tokenInfo;

        return convertToSet(rawAcl);
    }


    /**
     * Tests compression and decompression of the ACL table using {@link CompressedAclService}.
     *
     * @param aclTable The ACL table to compress and decompress.
     */
    public void testEncodeDecode(Map<String, Map<String, Set<String>>> aclTable){

        String comporedAcl = CompressedAclService.compressAcl(aclTable);

        log.info("Compressed ACL: " + comporedAcl);

        Map<String, Map<String, Set<String>>> decompressedAcl = CompressedAclService.decompressAcl(comporedAcl);

        log.info("Decompressed ACL: " + decompressedAcl);
    }
    
}
