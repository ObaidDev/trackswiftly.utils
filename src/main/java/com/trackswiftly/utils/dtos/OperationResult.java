package com.trackswiftly.utils.dtos;



/**
 * Represents the result of a database or business operation.
 * 
 * @param affectedRecords the number of records affected by the operation
 * @param message a message describing the outcome of the operation
 */

public record OperationResult(
    int affectedRecords,
    String message
) {


    /**
     * Creates an {@code OperationResult} with a default success message.
     *
     * @param affectedRecords the number of records affected
     * @return an {@code OperationResult} instance with a default message
     */
    public static OperationResult of(int affectedRecords) {
        return new OperationResult(affectedRecords, "Operation completed successfully.");
    }

    /**
     * Creates an {@code OperationResult} with a custom message.
     *
     * @param affectedRecords the number of records affected
     * @param message the custom message describing the result
     * @return an {@code OperationResult} instance with the provided message
     */
    public static OperationResult of(int affectedRecords, String message) {
        return new OperationResult(affectedRecords, message);
    }

}