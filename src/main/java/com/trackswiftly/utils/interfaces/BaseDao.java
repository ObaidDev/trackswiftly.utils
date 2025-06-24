package com.trackswiftly.utils.interfaces;

import java.util.List;

/**
 * Base generic DAO interface for performing standard CRUD operations.
 *
 * @param <T> the type of the entity
 * @param <I> the type of the entity's identifier
 */
public interface BaseDao <T, I>{

     /**
     * Inserts a list of entities in batch.
     *
     * @param entities the list of entities to insert
     * @return the list of inserted entities
     */
    List<T> insertInBatch(List<T> entities);

    
    /**
     * Deletes entities by their IDs.
     *
     * @param ids the list of entity IDs to delete
     * @return the number of entities deleted
     */
    int deleteByIds(List<I> ids);

    
    /**
     * Finds entities by their IDs.
     *
     * @param ids the list of entity IDs to find
     * @return the list of found entities
     */
    List<T> findByIds(List<I> ids);
    

    /**
     * Retrieves entities with pagination.
     *
     * @param page the page number (0-based)
     * @param pageSize the number of entities per page
     * @return the paginated list of entities
     */
    List<T> findWithPagination(int page, int pageSize);
    
    
    /**
     * Counts the total number of entities.
     *
     * @return the total entity count
     */
    Long count();


    /**
     * Updates a batch of entities by their IDs.
     *
     * @param ids the list of entity IDs to update
     * @param entity the entity data to apply to each ID
     * @return the number of entities updated
     */
    public int updateInBatch(List<I> ids, T entity) ;
}