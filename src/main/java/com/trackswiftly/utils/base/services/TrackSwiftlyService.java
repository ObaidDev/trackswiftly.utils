package com.trackswiftly.utils.base.services;

import java.lang.reflect.Method;
import java.util.List;

import com.trackswiftly.utils.annotations.ValidateOwnership;
import com.trackswiftly.utils.dtos.OperationResult;
import com.trackswiftly.utils.interfaces.TrackSwiftlyServiceInterface;

public abstract class TrackSwiftlyService<T, I, O> implements TrackSwiftlyServiceInterface<T, I, O> {

    protected abstract List<O> performCreateEntities(List<I> requests);
    protected abstract OperationResult performUpdateEntities(List<T> ids, I request);

    protected abstract void validateCreate(List<I> requests);
    protected abstract void validateUpdate(List<T> ids, I request);


     // Final methods enforce validation execution before actual logic
    public final List<O> createEntities(List<I> requests) {
        executeValidation("createEntities", requests);
        return performCreateEntities(requests);
    }

    public final OperationResult updateEntities(List<T> ids, I request) {
        executeValidation("updateEntities", ids, request);
        return performUpdateEntities(ids, request);
    }


    private void executeValidation(String methodName, Object... args) {
        try {
            Method method = this.getClass().getMethod(methodName, List.class);
            if (method.isAnnotationPresent(ValidateOwnership.class)) {
                System.out.println("Executing ownership validation for method: " + methodName);
                if ("createEntities".equals(methodName)) {
                    validateCreate((List<I>) args[0]);
                } else if ("updateEntities".equals(methodName)) {
                    validateUpdate((List<T>) args[0], (I) args[1]);
                }
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Method not found: " + methodName, e);
        }
    }
}
