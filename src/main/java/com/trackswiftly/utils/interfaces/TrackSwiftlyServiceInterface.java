package com.trackswiftly.utils.interfaces;

import java.util.List;

import com.trackswiftly.utils.dtos.OperationResult;
import com.trackswiftly.utils.dtos.PageDTO;

/***
 * 
 * T == Id Type of the entity for example Long
 * I == Input DTO Request
 * O == Output DTO Response
 */
public interface TrackSwiftlyServiceInterface <T , I , O>{

    public List<O> createEntities(List<I> requests);


    public OperationResult deleteEntities(List<T> ids);


    public List<O> findEntities(List<T> ids) ;



    public PageDTO<O> pageEntities(int page, int pageSize) ;


    public OperationResult updateEntities(List<T> ids, I request) ;


    public List<O> search(String keyword) ;
    
}