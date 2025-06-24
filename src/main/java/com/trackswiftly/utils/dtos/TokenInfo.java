package com.trackswiftly.utils.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trackswiftly.utils.enums.HttpMethod;
import com.trackswiftly.utils.enums.Resource;





/**
 * Specialized {@link HashMap} for holding token information with validation.
 * <p>
 * Validates keys as resource paths and values for valid HTTP methods when inserting entries.
 */
public class TokenInfo extends HashMap<String, Object>{



    /**
     * Constructs an empty {@code TokenInfo} map.
     */
    public TokenInfo() {
        super();
    }



    /**
     * Associates the specified value with the specified key in this map after validation.
     *
     * @param key   the resource path key
     * @param value the value associated with the resource path
     * @return the previous value associated with the key, or {@code null} if none
     * @throws IllegalArgumentException if the key or value is invalid
     */
    @Override
    public Object put(String key, Object value) {
        validateKeyValue(key, value);
        return super.put(key, value);
    }




    /**
     * Validates the key and value according to resource and HTTP method rules.
     *
     * @param key   the resource path
     * @param value the value associated with the key
     * @throws IllegalArgumentException if validation fails
     */
    private void validateKeyValue(String key, Object value) {
        validatePath(key);
        validateHttpMethods(value);
    }


    /**
     * Validates that the given key is a valid resource path.
     *
     * @param key the resource path to validate
     * @throws IllegalArgumentException if the key is not a valid resource path
     */
    private void validatePath(String key) {
        if (!Resource.isValidPath(key)) {
            throw new IllegalArgumentException("Invalid resource path: " + key);
        }
    }



    /**
     * Validates that the HTTP methods in the value are valid {@link HttpMethod} entries.
     * If the value is not a {@link Map} or contains no "methods" key, validation is skipped.
     *
     * @param value the value to validate
     * @throws IllegalArgumentException if any method is invalid
     */
    private void validateHttpMethods(Object value) {
        if (!(value instanceof Map)) {
            return;
        }
        Map<?, ?> valueMap = (Map<?, ?>) value;
        if (!valueMap.containsKey("methods")) {
            return;
        }
        List<?> methods = (List<?>) valueMap.get("methods");
        for (Object method : methods) {
            validateSingleMethod(method);
        }
    }

    

    /**
     * Validates a single HTTP method string.
     *
     * @param method the method to validate
     * @throws IllegalArgumentException if the method is not a valid {@link HttpMethod}
     */
    private void validateSingleMethod(Object method) {
        if (!(method instanceof String)) {
            throw new IllegalArgumentException("HTTP method must be a string");
        }
        String methodName = ((String) method).toUpperCase();
        try {
            HttpMethod.valueOf(methodName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid HTTP method: " + method);
        }
    }

}