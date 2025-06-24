package com.trackswiftly.utils.base.services;

import java.util.List;

import com.trackswiftly.utils.dtos.OperationResult;
import com.trackswiftly.utils.interfaces.TrackSwiftlyServiceInterface;

import lombok.extern.log4j.Log4j2;



/**
 * Abstract base service providing template methods for creating and updating entities.
 *
 * <p>Subclasses must implement validation and actual business logic.
 *
 * @param <T> the type representing entity identifiers
 * @param <I> the type representing input requests
 * @param <O> the type representing output results
 */
@Log4j2
public abstract class TrackSwiftlyServiceAbstract<T, I, O> implements TrackSwiftlyServiceInterface<T, I, O> {


    /**
     * Performs the actual creation of entities after validation.
     *
     * @param requests the list of input requests to create entities from
     * @return the list of created entities or results
     */
    protected abstract List<O> performCreateEntities(List<I> requests);



    /**
     * Performs the actual update of entities after validation.
     *
     * @param ids the list of entity identifiers to update
     * @param request the update request data
     * @return the result of the update operation
     */
    protected abstract OperationResult performUpdateEntities(List<T> ids, I request);


     /**
     * Validates the creation request inputs before processing.
     *
     * @param requests the list of input requests to validate
     */
    protected abstract void validateCreate(List<I> requests);


    /**
     * Validates the update request inputs before processing.
     *
     * @param ids the list of entity identifiers to validate
     * @param request the update request data to validate
     */
    protected abstract void validateUpdate(List<T> ids, I request);


    /**
     * Template method that performs validation and delegates creation logic.
     *
     * @param requests the list of input requests to create entities from
     * @return the list of created entities or results
     */
    public final List<O> createEntities(List<I> requests) {
        validateCreate(     requests    );
        return performCreateEntities(requests);
    }


    /**
     * Template method that performs validation and delegates update logic.
     *
     * @param ids the list of entity identifiers to update
     * @param request the update request data
     * @return the result of the update operation
     */
    public final OperationResult updateEntities(List<T> ids, I request) {
        validateUpdate( ids, request);
        return performUpdateEntities(ids, request);
    }
}
