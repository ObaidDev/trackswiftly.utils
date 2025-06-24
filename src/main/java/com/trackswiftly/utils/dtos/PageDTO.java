package com.trackswiftly.utils.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Generic Data Transfer Object (DTO) for paginated results.
 *
 * <p>Encapsulates a single page of content along with pagination metadata.
 * Implements {@link Serializable} for potential use in distributed systems.
 *
 * @param <T> the type of elements contained in the page
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDTO<T> implements Serializable{
    
    private static final long serialVersionUID = 1L;


    /**
     * The list of elements on the current page.
     */
    private List<T> content;
    
    /**
     * The current page number (0-based).
     */
    private int page;
    
    /**
     * The size of the page (number of elements per page).
     */
    private int size;


    /**
     * The total number of elements across all pages.
     */
    private long totalElements;


    /**
     * The total number of pages available.
     */
    private int totalPages;
}