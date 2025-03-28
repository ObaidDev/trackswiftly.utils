package com.trackswiftly.utils.base.services;

import java.util.List;

import com.trackswiftly.utils.dtos.OperationResult;
import com.trackswiftly.utils.interfaces.TrackSwiftlyServiceInterface;

import lombok.extern.log4j.Log4j2;


@Log4j2
public abstract class TrackSwiftlyService<T, I, O , E> implements TrackSwiftlyServiceInterface<T, I, O> {

    protected abstract List<O> performCreateEntities(List<I> requests);
    protected abstract OperationResult performUpdateEntities(List<T> ids, I request);

    protected abstract void validateCreate(List<I> requests);
    protected abstract void validateUpdate(List<T> ids, I request);


     // Final methods enforce validation execution before actual logic
    public final List<O> createEntities(List<I> requests) {
        validateCreate(     requests    );
        return performCreateEntities(requests);
    }

    public final OperationResult updateEntities(List<T> ids, I request) {
        validateUpdate( ids, request);
        return performUpdateEntities(ids, request);
    }
}
