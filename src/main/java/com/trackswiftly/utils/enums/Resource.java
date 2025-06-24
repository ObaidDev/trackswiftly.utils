package com.trackswiftly.utils.enums;




/**
 * Enum representing the different resource types supported in the system.
 * Each resource is associated with a specific path string.
 */
public enum Resource {

    /** MQTT resource (for message subscriptions). */
    MQTT("mqtt"),

    /** Devices resource path. */
    DEVICES("gw/devices"),

    /** Geofences resource path. */
    GEOFENCES("gw/geofences"),


    /** Calculations resource path. */
    CALCS("gw/calcs") ,

    /** Points of interest resource path. */
    POIS("gw/pois") ;

    private final String path;


    /**
     * Constructs a {@code Resource} with the specified path.
     *
     * @param path the path associated with the resource
     */
    Resource(String path) {
        this.path = path;
    }


    /**
     * Returns the path associated with this resource.
     *
     * @return the resource path
     */
    public String getPath() {
        return path;
    }

    /**
     * Checks if the given path is a valid resource path defined in this enum.
     *
     * @param path the path to validate
     * @return {@code true} if the path matches a defined resource; {@code false} otherwise
     */
    public static boolean isValidPath(String path) {
        for (Resource resource : values()) {
            if (resource.getPath().equals(path)) {
                return true;
            }
        }
        return false;
    }
}
