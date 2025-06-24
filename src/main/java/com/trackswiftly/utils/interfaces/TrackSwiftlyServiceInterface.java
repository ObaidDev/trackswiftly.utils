package com.trackswiftly.utils.interfaces;

import java.util.List;

import com.trackswiftly.utils.dtos.OperationResult;
import com.trackswiftly.utils.dtos.PageDTO;

/**
 * Generic service interface for CRUD and search operations on entities.
 *
 * @param <T> the type of the entity identifier (e.g., Long)
 * @param <I> the input DTO request type
 * @param <O> the output DTO response type
 */
public interface TrackSwiftlyServiceInterface <T , I , O>{


    /**
     * Creates entities based on the given list of input requests.
     *
     * @param requests the list of input DTOs for creation
     * @return a list of output DTOs representing created entities
     */
    public List<O> createEntities(List<I> requests);



    /**
     * Deletes entities with the given identifiers.
     *
     * @param ids the list of entity IDs to delete
     * @return an {@link OperationResult} indicating the outcome of the delete operation
     */
    public OperationResult deleteEntities(List<T> ids);



    /**
     * Finds entities by their identifiers.
     *
     * @param ids the list of entity IDs to find
     * @return a list of output DTOs representing found entities
     */
    public List<O> findEntities(List<T> ids) ;




    /**
     * Retrieves a paginated list of entities.
     *
     * @param page the page number (0-based)
     * @param pageSize the size of each page
     * @return a {@link PageDTO} containing the entities and pagination metadata
     */
    public PageDTO<O> pageEntities(int page, int pageSize) ;



    /**
     * Updates entities with the given IDs using the provided request data.
     *
     * @param ids the list of entity IDs to update
     * @param request the update request DTO
     * @return an {@link OperationResult} indicating the outcome of the update operation
     */
    public OperationResult updateEntities(List<T> ids, I request) ;



    /**
     * Searches entities matching the given keyword.
     *
     * @param keyword the search keyword
     * @return a list of output DTOs matching the search criteria
     */
    public List<O> search(String keyword) ;
    
}