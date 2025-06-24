package com.trackswiftly.utils.enums;



/**
 * Enum representing standard HTTP methods used in RESTful APIs.
 * <p>
 * These methods define the action to be performed on a given resource.
 */
public enum HttpMethod {

    /** HTTP GET method - typically used to retrieve data. */
    GET,

    /** HTTP POST method - typically used to create new resources. */
    POST,

    /** HTTP PUT method - typically used to update an entire resource. */
    PUT,

    /** HTTP DELETE method - used to delete a resource. */
    DELETE,

    /** HTTP PATCH method - used to partially update a resource. */
    PATCH,

    /** HTTP OPTIONS method - used to describe communication options for the target resource. */
    OPTIONS,

    /** HTTP HEAD method - similar to GET but returns only headers. */
    HEAD;

}
