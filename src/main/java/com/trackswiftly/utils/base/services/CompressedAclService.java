package com.trackswiftly.utils.base.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.trackswiftly.utils.enums.HttpMethod;
import com.trackswiftly.utils.enums.Resource;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CompressedAclService {



    private CompressedAclService() {}

    private static final Map<String, Integer> RESOURCE_MAP = new HashMap<>();
    private static final Map<Integer, String> REVERSE_RESOURCE_MAP = new HashMap<>();
    
    private static final Map<String, Integer> METHOD_MAP = new HashMap<>();
    private static final Map<Integer, String> REVERSE_METHOD_MAP = new HashMap<>();



    static {
        // Resources - using Resource enum
        int resourceIndex = 1;
        for (Resource resource : Resource.values()) {
            addMapping(RESOURCE_MAP, REVERSE_RESOURCE_MAP, resource.getPath(), resourceIndex++);
        }

        // Methods - using HttpMethod enum
        int methodIndex = 1;
        for (HttpMethod method : HttpMethod.values()) {
            addMapping(METHOD_MAP, REVERSE_METHOD_MAP, method.name(), methodIndex++);
        }
        
        // Special case for MQTT "subscribe" method which isn't an HTTP method
        addMapping(METHOD_MAP, REVERSE_METHOD_MAP, "subscribe", methodIndex);
    }


    private static void addMapping(Map<String, Integer> forward, Map<Integer, String> reverse, String key, Integer value) {
        forward.put(key, value);
        reverse.put(value, key);
    }


    private static Integer getOrCreateResourceId(String resource) {
        Integer resourceId = RESOURCE_MAP.getOrDefault(resource, 0);
        
        if (resourceId == 0) {
            // If resource not in map, we add it dynamically
            resourceId = RESOURCE_MAP.size() + 1;
            addMapping(RESOURCE_MAP, REVERSE_RESOURCE_MAP, resource, resourceId);
        }
        
        return resourceId;
    }


    private static Integer getOrCreateMethodId(String method) {
        Integer methodId = METHOD_MAP.getOrDefault(method, 0);
        if (methodId == 0) {
            // If method not in map, we add it dynamically
            methodId = METHOD_MAP.size() + 1;
            addMapping(METHOD_MAP, REVERSE_METHOD_MAP, method, methodId);
        }
        return methodId;
    }


    private static String convertMethodsToIdString(Set<String> methods) {
        List<Integer> methodIds = new ArrayList<>();
        
        for (String method : methods) {
            methodIds.add(getOrCreateMethodId(method));
        }
        
        return methodIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }


    public static String compressAcl(Map<String, Map<String, Set<String>>> acl) {

        log.debug("Compressing ACL: {}", acl);

        StringBuilder compressed = new StringBuilder();
        
        for (Map.Entry<String, Map<String, Set<String>>> resourceEntry : acl.entrySet()) {
            String resource = resourceEntry.getKey();
            Integer resourceId = getOrCreateResourceId(resource);
            
            Map<String, Set<String>> attributeMap = resourceEntry.getValue();
            Set<String> methods = attributeMap.get("methods");
            
            if (methods != null && !methods.isEmpty()) {
                compressed.append(resourceId)
                         .append(":")
                         .append(convertMethodsToIdString(methods))
                         .append(";");
            }
        }
        
        log.debug("Compressed ACL: {}", compressed.toString());

        return compressed.toString();
    }



    /**
     * Processes a single resource entry from compressed format
     * @param entry Entry in format "resourceId:methodId1,methodId2,..."
     * @return Map entry with resource name as key and methods map as value, or null if invalid
     */
    private static Map.Entry<String, Map<String, Set<String>>> processResourceEntry(String entry) {
        if (entry.isEmpty()) {
            return null;
        }
        
        String[] parts = entry.split(":");
        if (parts.length != 2) {
            return null;
        }
        
        Integer resourceId = Integer.parseInt(parts[0]);
        String resource = REVERSE_RESOURCE_MAP.get(resourceId);
        
        if (resource == null) {
            // Handle unmapped resource (should not happen if maps are consistent)
            return null;
        }
        
        Set<String> methods = processMethodIds(parts[1]);
        Map<String, Set<String>> resourceMap = new HashMap<>();
        resourceMap.put("methods", methods);
        
        return Map.entry(resource, resourceMap);
    }


    /**
     * Converts a comma-separated string of method IDs to a set of method names
     */
    private static Set<String> processMethodIds(String methodIdsStr) {
        Set<String> methods = new HashSet<>();
        String[] methodIdParts = methodIdsStr.split(",");
        
        for (String methodIdStr : methodIdParts) {
            Integer methodId = Integer.parseInt(methodIdStr);
            String method = REVERSE_METHOD_MAP.get(methodId);
            if (method != null) {
                methods.add(method);
            }
        }
        
        return methods;
    }


    public static Map<String, Map<String, Set<String>>> decompressAcl(String compressedAcl) {
        
        log.debug("Decompressing ACL: {}", compressedAcl);
        
        Map<String, Map<String, Set<String>>> acl = new HashMap<>();
        
        if (compressedAcl == null || compressedAcl.isEmpty()) {
            return acl;
        }
        
        String[] resourceEntries = compressedAcl.split(";");
        
        for (String entry : resourceEntries) {
            Map.Entry<String, Map<String, Set<String>>> resourceEntry = processResourceEntry(entry);
            if (resourceEntry != null) {
                acl.put(resourceEntry.getKey(), resourceEntry.getValue());
            }
        }
        
        return acl;
    }
    
}
